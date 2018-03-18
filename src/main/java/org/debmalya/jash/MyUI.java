package org.debmalya.jash;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import javax.servlet.annotation.WebServlet;

import org.joda.time.Days;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = 8441945713990649091L;

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
				ChronoLocalDate nineteen70 = null;
				
				long years = 0;
				long months = 0;
				long days = 0;
				long actualDays = 0;

				
				if (dateOfBirthValue.get().isAfter(nineteen70)) {
					years = ChronoUnit.YEARS.between(dateOfBirthValue.get(), LocalDate.now());
					months = ChronoUnit.MONTHS.between(dateOfBirthValue.get(), LocalDate.now());
					days = ChronoUnit.DAYS.between(dateOfBirthValue.get(), LocalDate.now());
					actualDays = days;
					// one year has 365 days
					// years = days / 365;
					days = days % 365;
					months = days / 30;
					days = days % 30;
				}

				comment += ", your age :" + years + " years " + months + " months " + days + " days. (Total "
						+ actualDays + " days).";

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
