package com.effourt.calenkit.controller;

import com.effourt.calenkit.domain.Member;
import com.effourt.calenkit.dto.*;
import com.effourt.calenkit.exception.CodeMismatchException;
import com.effourt.calenkit.exception.MemberNotFoundException;
import com.effourt.calenkit.service.JoinService;
import com.effourt.calenkit.service.LoginService;
import com.effourt.calenkit.util.EmailSend;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final JoinService joinService;
    private final MessageSource ms;
    private final EmailSend emailSend;
    private final PasswordEncoder passwordEncoder;

    @Value("${kakao.client-id}")
    private String kakaoClientId;

    /**
     * 로그인 페이지로 이동
     */
    @GetMapping("/form")
    public String login() {
        return "login/login";
    }

    /**
     * 로그인 후 Redirect 할 경로 지정
     * @param session
     * @return
     */
    @GetMapping("/return-uri")
    public String returnURI(HttpSession session) {
        String returnURI = (String)session.getAttribute("returnURI");
        log.info("returnURI = {}",returnURI);
        if (returnURI == null) {
            return "redirect:/";
        } else if (!returnURI.equals("")) {
            session.removeAttribute("returnURI");
            return "redirect:"+returnURI;
        }
        return "redirect:/";
    }

    /**
     * 로그아웃
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        log.info("로그아웃 시작");
        session.invalidate();
        log.info("로그아웃 종료");
        return "redirect:/login/form";
    }

    /**
     * 소셜 로그인
     * @param code
     * @param session
     * @return
     */
    @GetMapping("/kakao")
    public String loginByKakao(@RequestParam String code, HttpSession session) {
        AccessTokenRequest accessTokenRequest = AccessTokenRequest.builder()
                .clientId(kakaoClientId)
                .grantType("authorization_code")
                .redirectUri("http://localhost:8080/login/kakao")
                .code(code)
                .build();
        //Access 토큰 발급
        AccessTokenResponse accessToken = loginService.getAccessToken(accessTokenRequest);
        //Access 토큰으로 카카오 리소스 서버에서 사용자 정보 가져오기
        AuthUserInfoResponse userInfo = loginService.getAuthUserInfo(accessToken.getAccessToken());
        log.info("userEmail={}", userInfo.getEmail());

        Member member = loginService.getMemberById(userInfo.getEmail());
        if (member == null) {
            //사용자 이메일이 DB에 존재하지 않은 경우 회원가입 후 로그인
            joinService.joinBySns(userInfo);
            log.info("카카오 - 회원가입 후 로그인");
        } else {
            //탈퇴회원인 경우, 일반 회원으로 권한 변경
            if (member.getMemStatus() == 0) {
                member.setMemStatus(1);
                loginService.update(member);
            }
            log.info("카카오 - 탈퇴회원 재가입");
        }

        session.setAttribute("loginId", userInfo.getEmail());
        loginService.updateLastLogin(userInfo.getEmail());
        return "redirect:/login/return-uri";
    }
    
    /**
     * 아이디, 비밀번호 존재 여부 체크
     * 아이디 존재 O, 비밀번호 O : PASSWORD_LOGIN
     * 아이디 존재 O, 비밀번호 X : CODE_LOGIN
     * 아이디 존재 X, 비밀번호 X : JOIN_LOGIN
     * @param idMap
     * @return
     */
    @PostMapping("/check")
    @ResponseBody
    public String checkId(@RequestBody Map<String, String> idMap) {
        String memId = idMap.get("id");
        if (memId == null) {
            return "이메일이 올바르지 않습니다.";
        }
        String loginType = loginService.checkLoginType(memId);
        log.info("loginType={}", loginType);

        return loginType;
    }

    @PostMapping("/send-code")
    @ResponseBody
    public String sendCode(@RequestBody LoginRequest loginRequest, HttpSession session) {
        String memId = loginRequest.getId();
        String subject = ms.getMessage("mail.login-code.subject", null, null);
        String message = ms.getMessage(
                "mail.login-code.message",
                new Object[]{emailSend.createAccessCode(memId, session)},
                null);

        EmailMessage emailMessage = EmailMessage.builder()
                .recipient(memId)
                .subject(subject)
                .message(message)
                .build();
        //이메일 전송
        emailSend.sendMail(emailMessage);

        log.info("email id={}", memId);
        log.info("subject={}", subject);
        log.info("message={}", message);
        return "OK";
    }

    /**
     * 패스워드로 로그인
     * @param member
     * @param session
     * @return
     */
    @PostMapping("/password")
    @ResponseBody
    public String loginByPassword(@RequestBody Member member, HttpSession session) {
        //세션에 저장된 아이디 검색
        Member findMember = loginService.getMemberById(member.getMemId());

        //회원 존재 여부 및 탈퇴 회원 여부 검증
        if (findMember == null || findMember.getMemStatus() == 0) {
            throw new MemberNotFoundException(member.getMemId());
        }

        //전달된 비밀번호와 검색한 비밀번호(인코딩된 비밀번호)를 비교
        if (member.getMemPw() == null || !passwordEncoder.matches(member.getMemPw(), findMember.getMemPw())) {
            return "비밀번호가 올바르지 않습니다.";
        } else {
            session.setAttribute("loginId", member.getMemId());
            loginService.updateLastLogin(findMember.getMemId());
        }
        return "OK";
    }

    /**
     * 로그인 코드로 로그인
     * @param loginRequest
     * @param session
     * @return
     */
    @PostMapping("/login-code")
    @ResponseBody
    public String loginByCode(@RequestBody LoginRequest loginRequest, HttpSession session) {
        String memId = loginRequest.getId();
        //회원 존재 여부 및 탈퇴 회원 여부 검증
        Member member = loginService.getMemberById(memId);
        if (member == null || member.getMemStatus() == 0) {
            throw new MemberNotFoundException(memId);
        }

        String code = (String) session.getAttribute(loginRequest.getLoginCode());
        //코드 존재 여부 및 일치 여부 검증
        if (code == null || !code.equals(memId + "ACCESS")) {
            throw new CodeMismatchException(code);
        } else {
            session.setAttribute("loginId", memId);
            loginService.updateLastLogin(memId);
        }
        return "OK";
    }

    /**
     * 회원가입 코드로 회원가입 후 이메일 로그인
     * @param loginRequest
     * @param session
     * @return
     */
    @PostMapping("/register-code")
    @ResponseBody
    public String loginByJoin(@RequestBody LoginRequest loginRequest, HttpSession session) {
        String memId = loginRequest.getId();
        String code = (String) session.getAttribute(loginRequest.getRegisterCode());
        //코드 존재 여부 및 일치 여부 검증
        if (code == null || !code.equals(memId + "ACCESS")) {
            throw new CodeMismatchException(code);
        }

        Member member = loginService.getMemberById(memId);
        if (member != null && member.getMemStatus() == 0) {
            member.setMemStatus(1);
            loginService.update(member);
            session.setAttribute("loginId", memId);
            loginService.updateLastLogin(memId);
            return "RE_JOIN";
        }

        return "JOIN";
    }

    /**
     * 비밀번호 초기화 코드로 로그인 - 비밀번호 초기화 후 로그인 처리
     * @param loginRequest
     * @param session
     * @return
     */
    @PostMapping("/initialize-code")
    @ResponseBody
    public String loginByInitialize(@RequestBody LoginRequest loginRequest, HttpSession session) {
        String memId = loginRequest.getId();
        //회원 존재 여부 및 탈퇴 회원 여부 검증
        Member member = loginService.getMemberById(memId);
        if (member == null || member.getMemStatus() == 0) {
            throw new MemberNotFoundException(memId);
        }

        //초기화 코드 검증 후 로그인
        String code = (String) session.getAttribute(loginRequest.getInitializeCode());
        if (code == null || !code.equals(memId + "ACCESS")) {
            throw new CodeMismatchException(code);
        } else {
            //비밀번호 초기화 (null로 지정)
            loginService.updatePassword(memId, null);
            session.setAttribute("loginId", memId);
            loginService.updateLastLogin(memId);
        }
        return "OK";
    }
}
