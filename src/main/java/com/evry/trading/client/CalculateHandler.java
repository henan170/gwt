package com.evry.trading.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.evry.trading.client.GreetingService;
import com.evry.trading.client.GreetingServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CalculateHandler implements ClickHandler {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	@Override
	public void onClick(ClickEvent event) {
		System.out.println("Check logging, log4j or util?, hmm client code actually");

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		greetingService.greetServer("MyText", new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				dialogBox.setText("Remote Procedure Call - Failure");
				serverResponseLabel.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(SERVER_ERROR);
				dialogBox.center();
				closeButton.setFocus(true);
			}
			public void onSuccess(String result) {
				dialogBox.setText("Remote Procedure Call");
				serverResponseLabel.removeStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(result);
				dialogBox.center();
				closeButton.setFocus(true);
			}
		});
	}
}
