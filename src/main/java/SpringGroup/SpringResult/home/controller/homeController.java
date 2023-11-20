package SpringGroup.SpringResult.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {
  @GetMapping("/") // localhost:8080
  public String home() {
    return "home"; // home.html 호출
  }
}
