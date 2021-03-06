package acme.features.administrator.spam;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.spam.Spam;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

@Controller
@RequestMapping("/administrator/spam")
public class AdministratorSpamController extends AbstractController<Administrator, Spam > {
	
	@Autowired
	protected AdministratorSpamShowService adminSpamShowService;
	@Autowired
	protected AdministratorSpamUpdateService adminSpamUpdateService;
	
		
	@PostConstruct
	protected void iniatialise() {
		super.addBasicCommand(BasicCommand.SHOW, this.adminSpamShowService);
		super.addBasicCommand(BasicCommand.UPDATE, this.adminSpamUpdateService);
	}

}
