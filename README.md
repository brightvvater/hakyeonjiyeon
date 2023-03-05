<h1>💻 학연지연 프로젝트 (2023)</h1>
<h2>기간: 2023/02/12~</h2>
<h2>링크</h2>
<a href= "http://ec2-52-78-107-16.ap-northeast-2.compute.amazonaws.com:8080/"></a>

<h4>참여인원: 1명</h4>
<h4>프로젝트 주제: 오프라인 레슨 플랫폼 - 학연지연 프로젝트</h4>
<h4>사용기술: JAVA, SPRING BOOT, JPA, AWS EC2, AWS S3, AWS RDS, H2 Database, Spring Security, OAuth2, Git, Thymeleaf</h4>
<h4>선정배경</h4>
자기 개발을 원하는 성인들이 늘어감에 따라 성인들을 가르칠 수 있는 학원들이 늘어나고 있다. 하지만 이를 연결시켜주는 플랫폼이 부족하다는 생각이 들어 오프라인 레슨을 들을 수 있는 학원과 그 학원의 강사를 노출시켜 홍보할 수 있는 플랫폼을 만들고 싶다는 취지로 토이 프로젝트를 진행하게 되었다.
<h4>주요 내용</h4>
erd 및 클래스 다이어그램 설계 <p><p>
spring 내부기능을 활용한 validation 및 error message 지정 <p>
AWS ec2 및 rds 를 사용한 배포 <p>
AWS s3를 사용한 파일 업로드 <p>
JPA fetch join 을 사용한 어플리케이션 최적화 <p>
OAuth2 및 스프링 시큐리티를 사용한 로그인/ 회원가입 <p>
아임포트를 사용한 결제 시스템 api 활용(예정) <p>
CI/CD(예정) <p>
rest api(예정)<p>
query dsl사용한 페이징, 검색(예정)<p>

<h4>일정</h4>
2/12: 엔티티 설계 및 방향, 범위 설정
-> 시간이 많지 않기에 레슨을 확인할 수 있는 메인페이지와 그 세부내용을 확인할 수 있는 상세페이지, 그리고 주문이 가장 핵심 서비스라고 생각하였다. 핵심 서비스를 기준으로 하여 관리자 페이지, 일반 사용자 페이지로 나누어 이용할 수 있는 페이지를 나누는 것, 그리고 시간이나 능력이 허락한다면 게시판 페이지, 아임포트 API를 사용한 결제 페이지, 알림이나 채팅 등을 구현하고 싶다는 범위를 정하였다. 이를 기준으로 ERD를 설계하였다. <p> <p>
2/13 ~2/19 : 핵심 서비스의 대략적인 프로세스를 구현하였다. 전체적인 페이지를 구상하고 페이지를 클릭할 시 어느 부분으로 넘어가야 하는지, 그 안의 핵심적인 서비스를 만들었다(주로 생성과 조회 위주). 메인페이지와 상세페이지를 기준으로 repository와 service, controller 를 만들었고 로그인 관련 프로세스는 만들지 않았기 때문에 인증이 필요한 곳은 임시적인 member_id를 넣어서 해결하였다. 추후 변경이 필요한 것들은 주석이나 메모를 하여 변경이 필요하다는 것을 기록해 두었다. <p>
2/20 ~ 2/21: AWS EC2를 이용하여 배포에 성공하였다. 아직 프로젝트가 완성된 것은 아니었지만 이전 프로젝트에서 배포를 경험해 본 적이 없어 시행착오가 필요할 듯 하여 미리 배포를 연습할 겸 배포관련 책을 참고하여 배포를 하였다. 리눅스를 사용해 본 경험이 없어 CLI, VIM 파일 등을 다루는 데 애를 먹었다. <p>
2/22 ~ 2/23: 파일 업로드 관련 서비스를 추가하였다. 강의나 강사를 소개하는 데 있어 관리자의 파일 업로드는 필수적인 것이라 생각하여 도전하였다. S3를 사용하여 파일 업로드를 하는 것은 처음이었기에 구글링이 필요하였다. <p>
2/24 : 일반 로그인 기능을 구현하였다. Spring Security를 사용하였고 중복되는 user는 들어가지 못하도록 하는 validation check 등이 중요하게 느껴졌다. DB에 회원의 정보가 들어가게 하는 것은 어렵지 않았으나 회원의 정보를 가져오는 과정에서 Spring Security에서 제공하는 UserDetails에서는 username과 ROLE 만을 제공한다는 사실을 알게 되었다. username만을 가지고는 validation 체크를 할 수 없으므로 구글링을 하며 CustomUserDetail을 만들어서 회원이 직접 만든 아이디를 통해 중복체크를 할 수 있도록 구현하였다. <p>
2/25 ~ 2/27: CSS, 부트 스트랩을 사용한 UI/UX <p>
국비지원과정을 들으면서 CSS를 배우긴 했으나 깊이 있는 학습을 할 시간이 부족했기에 화면을 만지는 데 시간이 꽤 걸렸던 것 같다. 레이아웃은 반응형 웹을 만들고 싶어 부트 스트랩을 사용하였고 CSS를 가지고 정렬이나 폰트, 색 등을 입히는 것 위주로 진행하였다. 깔끔하게만 만들려고 했으나 생각보다 시간이 오래 걸리는 작업이었다. UI를 구성하면서 필요할 것 같은 게시판과 세부적인 메뉴들도 조금 더 추가했다. <p>
2/28 ~ 3/2 : 소셜로그인 기능 추가, OAuth2를 사용한 구글 로그인, 네이버 로그인을 추가해 보았다. DB에 데이터가 쌓이도록 만들었으나 회원정보를 수정하는 페이지를 만들 때, 일반 회원은 아이디가 모두 입력되어 있는 반면, 소셜로그인 회원은 아이디, 패스워드가 별도로 없고 이메일을 함부로 변경하지 못하도록 만들어야 하는 데 이 부분을 어떻게 손 봐야 할 지 아직 고민중이다.  <p>
앞으로 해야할 것 : CI/CD 구축, 결제 API, Query Dsl을 사용한 페이징, 검색 등, REST API를 위한 controller 추가, 이외 자잘한 수정 등  <p>

<h4>프로젝트를 진행하면서 느꼈던 점</h4>
토이 프로젝트를 해야겠다고 결심했던 건 국비지원학원을 마쳐갈 때 쯤이었던 것 같다. <p>
국비지원을 시작했던 2개월동안은 막막하고 어렵고 너무 생소한 개념들에 어지러웠다. 처음 모의 프로젝트를 받았을 때는 아직 아무것도 해내지 못한다는 좌절감만 너무 컸다. <p>
국비지원을 시작한지 4개월쯤 됐을 때는 이제 조금 알아듣고 재미를 약간 느끼게 됐던 것 같다. 파이널 프로젝트를 통해 개발의 즐거움을 조금 알게 되었다. <p>
파이널 프로젝트를 마치고 수료일이 다가오자 내가 아직 많은 것을 할 수 없다는 막막함과 아직 공부해보고 싶은 것이 많다는 기대감 등이 교차했던 것 같다. 내가 지금까지 배웠던 것들을 조금 더 다지고 확장해 나갈 수 있는 시간이 필요하다고 판단했고 학원에서 찍먹만 해보았던 JPA, AWS에 대한 흥미를 가지고 인터넷 강의를 들었다. <p>
프로젝트를 하기까지 1개월이 넘는 시간이 걸렸다. 필요한 강의만 빠르게 듣고 빨리 프로젝트에 적용하고 싶었으나 기초적인 개념이 없는 상태에서는 엔티티를 설계하는 것조차 감이 잡히지 않았다. 결국 김영한님의 Spring 강의 전부와 JPA 기초강의, 활용강의, 기초적인 AWS 강의 등을 듣고 나서야 시작할 수 있었다. <p>
강의 이론을 통해 들었던 의존성 주입 및 interface를 활용한 다형성, 메시지 기능, validation 기능, thymeleaf 의 다양한 spring 통합 기능, spring security, JPA의 페치조인 등을 응용해서 사용하고 싶다는 기대감과 열정이 가득했는데 막상 프로젝트에 적용시키다보니 많은 시행착오와 어려움이 있었다. 강의교재와 책 등을 참고해서 했으나 강의에서 봤던 것 처럼 깔끔하고 가독성 좋은 코드가 되지 못한 것 같아 아쉬움이 남는다. <p>
특히 JPA의 성능적인 부분을 잘 설계해보고 싶어 강의도 듣고 페치조인도 사용했는데 아직 실무감이 없어 어느 정도가 성능이 좋은 것인지, 쿼리가 얼마나 나가야 효율적인 코드가 되는 것인지에 대한 감각이 없어서 너무 아쉬웠다. <p>
배포를 경험해보고 어떤 웹사이트를 운영할 수 있다는 가능성을 봤을 때는 너무 기뻤다. 하지만 아직 CI/CD 관련한 Jenkins나 NginX에 대한 경험이 전무하여 이를 조금 더 공부하고 꼭 적용시켜 보고 싶고, 배포를 했음에도 불구하고 보안이나 시스템에 대한 지식이 부족하여 어떻게 해야 사이트를 안전하게 운영할 수 있는지에 대한 경험도 없다는 것을 느꼈다. <p>
아직은 서비스적으로나, 보안이나 UI적으로나 많이 부족한 프로젝트지만 처음부터 끝까지 혼자 구현해 보고 경험해봤다는 것에 큰 의의를 둔다. 이전 조별 프로젝트에서는 경험해보고 싶다는 욕심이 있었지만 시간적인 한계와 분담으로 인해 경험해 보지 못했던 로그인이나 UI 등 여러 부분을 해 볼 수 있어서 즐거웠다. 아직 공부해야할 부분이 너무 많다는 것도 알게 되어 조급함이 들지만 동시에 내가 성장해 나갈 수 있는 폭이 많이 남아있다는 것에서 즐거움을 느낀다. 꾸준히 공부하며 조금씩 이 프로젝트를 발전시켜 나갈 것이다. <p>

<h4>참조한 강의, 책</h4>
김영한님의 인프런 강의(Spring 핵심원리, MVC 1,2, DB 1,2, 자바 ORM 표준 JPA 프로그래밍) <p>
Spring boot와 AWS로 혼자 구현하는 웹서비스/이동욱 저 <p>
클라우드 서비스 AWS, AWS가입부터 활용까지 / 생활코딩 <p>
 
