package SpringGroup.SpringResult.domain.init.controller;

// Annotation 사용을 위해
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class helloController {
  @GetMapping("hello")
  public String hello(Model model) {
    model.addAttribute("data", "hello!!");
    // attribute 의 이름이 "data"인 곳에 value 로 "hello!!" 가 들어감
    return "hello";
  }

  @GetMapping("hello-mvc")
  public String helloMvc(@RequestParam("name") String name, Model model) { // 외부에서 param 을 받음
    model.addAttribute("name", name);
    return "hello-template";
  }

  @GetMapping("hello-string")
  @ResponseBody // http의 body에 param으로 받은 data를 직접 넣어줌
  public String helloString(@RequestParam("name") String name) {
    return "hello " + name; // param으로 받은 게 그대로 요청한 서버에 바로 전달
  }

  @GetMapping("hello-api")
  @ResponseBody // http의 body에 param으로 받은 data를 직접 넣어줌
  public Hello helloApi(@RequestParam("name") String name) {
    Hello hello = new Hello();
    hello.setName(name);
    return hello; // hello 객체를 return
  }

  static class Hello {
    private String name; // private 라서 메서드를 사용해 활용

    public String getName() { // getter
      return name;
    }

    public void setName(String name) { // setter
      this.name = name;
    }
  }
}
