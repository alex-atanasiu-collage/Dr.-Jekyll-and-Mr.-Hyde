/*
 * ClassName: MainController.java
 * Author: Alexandru-Marian Atanasiu, alexandru.atanasiu@outlook.com
 * Created: 20-10-2016
 * Last Updated: 20-10-2016
 * Version: 0.1 
 */
package service;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class MainController {
    @RequestMapping("/name")
    public String name(){
        return "Alex";
    }
}