package us.dontcareabout.gameRoom.client.agb.poker.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

/**
 * 一張撲克牌。
 * <p>
 * 備註：package name 還是用了「poker」這個對中文來講比較直覺的名稱。
 */
public class Card {
	public final Suit suit;
	public final int number;

	/**
	 * 注意：joker 的 suit 值為 {@link Suit#joker}，合法點數為 1 或 2
	 */
	@JsonCreator
	public Card(@JsonProperty("suit") Suit suit, @JsonProperty("number") int number) {
		Preconditions.checkArgument(suit != null);
		Preconditions.checkArgument(number > 0 && number < 14);

		if (Suit.joker == suit && number > 2) {	//應該沒法用 Preconditions
			throw new IllegalArgumentException();
		}

		this.suit = suit;
		this.number = number;
	}

	//下面是為了某些低水準的理由而預備的 code，後來發現沒有必要
	//不過寫了都寫了，那就... [逃]
	private static final char SPADE = '♠';
	private static final char HEART = '♥';
	private static final char DIAMOND = '♦';
	private static final char CLUB = '♣';
	private static final char JOKER = '☺';
	private static final String[] NUMBER = {
		"Ａ", "２", "３", "４", "５",
		"６", "７", "８", "９", "10",
		"Ｊ", "Ｑ", "Ｋ"
	};

	@Override
	public String toString() {
		return to(suit) + NUMBER[number - 1];
	}

	/**
	 * @param text {@link #toString()}
	 */
	public static Card from(String text) {
		String number = text.substring(1);
		int i = 0;
		for (; i < NUMBER.length; i++) {
			if (NUMBER[i].equals(number)) { break; }
		}

		return new Card(from(text.charAt(0)), i + 1);
	}

	private static char to(Suit suit) {
		switch (suit) {
		case club: return CLUB;
		case diamond: return DIAMOND;
		case heart: return HEART;
		case joker: return JOKER;
		case spade: return SPADE;
		default: return '？';	//don't care
		}
	}

	private static Suit from(char c) {
		switch (c) {
			case SPADE: return Suit.spade;
			case HEART: return Suit.heart;
			case DIAMOND: return Suit.diamond;
			case CLUB: return Suit.club;
			case JOKER: return Suit.joker;
			default: return null;	//don't care
		}
	}
}
