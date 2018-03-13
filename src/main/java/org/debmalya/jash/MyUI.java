package org.debmalya.jash;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout layout = new VerticalLayout();

		final TextField name = new TextField();
		name.setCaption("Type your name here:");

		final DateField dateOfBirth = new DateField();
		dateOfBirth.setCaption("Date of birth");

		Label clickComment = new Label();

		Button button = new Button("Click Me");
		button.addClickListener(e -> {
			Optional<LocalDate> dateOfBirthValue = dateOfBirth.getOptionalValue();
			String comment = "Thanks " + name.getValue();

			if (dateOfBirthValue.isPresent()) {
				long years = ChronoUnit.YEARS.between(dateOfBirthValue.get(), LocalDate.now());
				
				comment += ", your age :" + years
						+ " years .";
				
			} else {
				comment += ", it works!";
			}
			clickComment.setValue(comment);
		});

		layout.addComponents(name, dateOfBirth, button, clickComment);

		setContent(layout);
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
