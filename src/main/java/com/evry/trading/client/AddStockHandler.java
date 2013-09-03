package com.evry.trading.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.evry.trading.domain.Stock;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;

public class AddStockHandler implements ClickHandler, KeyDownHandler {

	private TradingClient stockWatcher;
	private List<String> stocks = new ArrayList<String>();

	public AddStockHandler(TradingClient stockWatcher) {
		this.stockWatcher = stockWatcher;
	}

	@Override
	public void onClick(ClickEvent event) {
		addStock();
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			addStock();
		}
	}

	/**
	 * Add stock to FlexTable. Executed when the user clicks the addStockButton
	 * or presses enter in the newSymbolTextBox.
	 */
	private void addStock() {

		TextBox newSymbolTextBox = stockWatcher.getNewSymbolTextBox();

		final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
		newSymbolTextBox.setFocus(true);

		// Stock code must be between 1 and 10 chars that are numbers, letters,
		// or dots.
		// TODO validate method 
		if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
			Window.alert("'" + symbol + "' is not a valid symbol.");
			newSymbolTextBox.selectAll();
			return;
		} else {
			newSymbolTextBox.setText("");
			// TODO Don't add the stock if it's already in the table.
			Stock stock = new Stock();
			stock.setSymbol(symbol);
			stock.setPrice(Random.nextInt());
			stock.setChange(Random.nextDouble());
			stocks.add(symbol);
			final FlexTable stocksFlexTable = stockWatcher.getStocksFlexTable();

			int newRowNumber = stocksFlexTable.getRowCount();
			stocksFlexTable.setText(newRowNumber, 0, stock.getSymbol());
			stocksFlexTable.setText(newRowNumber, 1, String.valueOf(stock.getPrice()));
			stocksFlexTable.setText(newRowNumber, 2, String.valueOf(stock.getChange()));

			// Add a button to remove this stock from the table.
			Button removeStockButton = new Button("x");
			removeStockButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					int removedIndex = stocks.indexOf(symbol);
					stocks.remove(removedIndex);
					stocksFlexTable.removeRow(removedIndex + 1);
				}
			});
			stocksFlexTable.setWidget(newRowNumber, 3, removeStockButton);
		}

	}
}
