package com.evry.trading.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TradingClient implements EntryPoint {

	private VerticalPanel mainPanel = new VerticalPanel();
	private FlexTable stocksFlexTable = new FlexTable();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox newSymbolTextBox = new TextBox();
	private Button addStockButton = new Button("Add");
	private Label lastUpdatedLabel = new Label();

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {
		addStockUI();
		addCalculateUI();
	}

	private void addStockUI() {
		// Create table for stock data.
		stocksFlexTable.setText(0, 0, "Symbol");
		stocksFlexTable.setText(0, 1, "Price");
		stocksFlexTable.setText(0, 2, "Change");
		stocksFlexTable.setText(0, 3, "Remove");

		// Assemble Add Stock panel.
		addPanel.add(newSymbolTextBox);
		addPanel.add(addStockButton);

		// Assemble Main panel.
		mainPanel.add(stocksFlexTable);
		mainPanel.add(addPanel);
		mainPanel.add(lastUpdatedLabel);

		// Associate the Main panel with the HTML host page.
		RootPanel.get("stockList").add(mainPanel);

		// Move cursor focus to the input box.
		newSymbolTextBox.setFocus(true);

		AddStockHandler addStockHandler = new AddStockHandler(this);
		addStockButton.addClickHandler(addStockHandler);
		newSymbolTextBox.addKeyDownHandler(addStockHandler);
	}

	private void addCalculateUI() {
		Button calcButton = new Button("Calculate");
		calcButton.addClickHandler(new CalculateHandler());
		RootPanel.get("stockList").add(calcButton);
	}

	public TextBox getNewSymbolTextBox() {
		return newSymbolTextBox;
	}

	public FlexTable getStocksFlexTable() {
		return stocksFlexTable;
	}
}
