package com.hjc.herolrouter.client.pay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/pay")
public class PayController {
	public Logger logger = LoggerFactory.getLogger(PayController.class);
}
