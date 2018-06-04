package com.designedperfectly.passwd;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswdController {

	@Inject
	PasswdValidation validation;

    @RequestMapping(value = "/passwd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PasswdBody> getPasswdResponse(@RequestBody PasswdBody passwdBody)
    {
    	ResponseEntity<PasswdBody> response = new ResponseEntity<PasswdBody>(new PasswdBody(validation.validatePasswd(passwdBody.getPasswd())), HttpStatus.OK);
        return response;
    }
}