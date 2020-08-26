package tfm.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import tfm.springboot.dtos.FullBudgetDTO;

@Service
public class EmailService {

	private JavaMailSender javaMailSender;

	final String SENDER = "acevedob@acevedo.biz";

	@Autowired
	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendMail(String toEmail, String subject, String message) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(toEmail);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		mailMessage.setFrom(SENDER);
		javaMailSender.send(mailMessage);
	}

	public String getTemplate(FullBudgetDTO budget) {
		
		return "Hello, " + budget.getCustomer().getName() + " " + budget.getCustomer().getLastname() + "\n\n" +
		"Here is your last Netsuite implantation budget registered: \n\n" +
		
		"Date: " + budget.getDate() + "\n" +
		"Company: " + budget.getCustomer().getCompany().getName() + "\n" +
		"Industry: " + budget.getCustomer().getCompany().getIndustry() + "\n" +
		"Country: " + budget.getCustomer().getCompany().getCountry() + "\n\n" +
		
		"Total implantation hours: " + budget.getTotal()  + "\n\n\n" +
		
		"I hope I've helped, thank you for trusting us. \n\nAcevedoapps IT Solutions";
	}
}