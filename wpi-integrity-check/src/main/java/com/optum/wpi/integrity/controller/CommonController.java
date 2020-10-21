package com.optum.wpi.integrity.controller;

import com.optum.wpi.integrity.model.Mismatch;
import com.optum.wpi.integrity.service.CommonDBService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/integrity")
public class CommonController {


    private static final Logger _LOGGER = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private CommonDBService commonDBService;

    @RequestMapping(value = "/schema", method = RequestMethod.GET, produces = { "application/json" })
    public @ResponseBody Map<String, String> validateSchema(HttpServletRequest request, HttpServletResponse response) {
        return commonDBService.validateSchema();
    }

}
