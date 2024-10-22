package com.gdu.prj09.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gdu.prj09.service.MemberService;

import lombok.RequiredArgsConstructor;

/*
 * RESTful
 * 1. REpresentation State Transfer
 * 2. 요청 주소를 작성하는 한 방식이다.
 * 3. 요청 파라미터를 ? 뒤에 추가하는 Query String 방식을 사용하지 않는다.
 * 4. 요청 파라미터는 주소에 포함하는 Path Variable 방식을 사용하거나, 요청 본문에 포함하는 방식을 사용한다.
 * 5. 요청의 구분을 "주소 + 메소드" 조합으로 구성한다.
 * 6. CRUD 요청 예시
 *            |  URL                       |   Method
 *   -------- |----------------------------|-----------          
 *    1) 목록 | /members                   |   GET
 *            | /members/page/1            |
 *            | /members/page/1/display20  |
 *    2) 상세 | /members/1                 |  GET
 *    3) 삽입 | /members                   |  POST
 *    4) 수정 | /members                   |  PUT     (POST와유사)
 *    5) 삭제 | /member/1                  |  DELETE (get과유사)
 *            | /members/1,2,3             |                      
 */  

@RequiredArgsConstructor
@Controller
public class MemberController {

  private final MemberService memberService;
  
  @GetMapping(value = "/admin/member.do")
  public void adminMember() {
    // 반환타입이 void 인 경우 주소를 JSP 경로로 인식한다.
    // /admin/member.do ======> /WEB-INF/views/admin/member.jsp
  }
  
  @PostMapping(value = "/members", produces = "application/json")
  public ResponseEntity<Map<String, Object>> registerMember(@RequestBody Map<String, Object> map,
                                                            HttpServletResponse response) {
    // 클라이언트가 전송한 http 요청의 본문에 포함된 데이터를 자바 객체로 변환하여 컨트롤러 메서드 파라미터로 주입
    // 클라이언트가 회원 등록 요청을 할 때, JSON 형태로 데이터를 http 요청 본문에 담아 전송하면 이 데이터를 @requestbody 를 통해 map 타입으로 변환하여 메서드에서 처리
    System.out.println(map);
    return memberService.registerMember(map, response);
  }
  
  @PutMapping(value = "/members", produces = "application/json")
  public ResponseEntity<Map<String, Object>> modifyMember(@RequestBody Map<String, Object> map) {
    System.out.println(map);
    return memberService.modifyMember(map);
  }
  
  @DeleteMapping(value = "/member/{memberNo}", produces = "application/json")
  public ResponseEntity<Map<String, Object>> removeMember(@PathVariable(value = "memberNo", required = false) Optional<String> opt) {
    int memberNo = Integer.parseInt(opt.orElse("0"));
    return memberService.removeMember(memberNo);
  }
  
  @DeleteMapping(value = "/members/{memberNoList}", produces = "application/json")
  public ResponseEntity<Map<String, Object>> removeMembers(@PathVariable(value = "memberNoList", required = false) Optional<String> opt) {
    return memberService.removeMembers(opt.orElse("0"));
  }
  
  @GetMapping(value = "/members/page/{p}/display/{dp}", produces = "application/json")
  public ResponseEntity<Map<String, Object>> getMembers(@PathVariable(value = "p", required = false) Optional<String> optPage
                                                      , @PathVariable(value = "dp", required = false) Optional<String> optDisplay) {
    // @PathVariable 은 URL 경로에 포함된 변수를 메서드 파라미터로 받아오는데 사용되는 어노테이션.
    // 즉, URL 일부를 변수로 사용하여, 그 값을 메서드 내부에서 활용할 수 있게 해줌.
    int page = Integer.parseInt(optPage.orElse("1"));
    int display = Integer.parseInt(optDisplay.orElse("20"));
    return memberService.getMembers(page, display);
  }
  
  @GetMapping(value = "/members/{memberNo}", produces = "application/json")
  public ResponseEntity<Map<String, Object>> getMemberByNo(@PathVariable(value = "memberNo", required = false) Optional<String> opt) {
    int memberNo = Integer.parseInt(opt.orElse("0"));
    return memberService.getMemberByNo(memberNo);
  }
  
}
