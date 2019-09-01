package com.mynotes.spring.cloud;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressControllers {

    @RequestMapping(value = "/company", method = RequestMethod.GET)
    @ResponseBody
    public String getContact() {
        return "Public mail Address";

    }

}
