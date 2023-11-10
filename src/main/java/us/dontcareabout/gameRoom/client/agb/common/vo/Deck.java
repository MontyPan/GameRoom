package us.dontcareabout.gameRoom.client.agb.common.vo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import us.dontcareabout.gameRoom.client.agb.poker.vo.Card;
import us.dontcareabout.gameRoom.client.agb.poker.vo.Suit;

/**
 * 一疊 ____（以下文件以「牌 / card」代稱）。
 * <p>
 * {@link #add(Object)}、{@link #deal()} 的行為與 stack 相同，為 LIFO。
 */
//注意：為了撰寫上方便，「第一張牌」其實是 List 的最後一個 element
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class Deck<T> {
	private List<T> sequence = Lists.newArrayList();

	public int amount() {
		return sequence.size();
	}

	public List<T> list() {
		return Collections.unmodifiableList(Lists.reverse(sequence));
	}

	/** 將一張牌放在牌堆最上面 */
	public void add(T card) {
		sequence.add(card);
	}

	/** 將一疊牌依序放在牌堆最上面 */
	public void add(Deck<T> deck) {
		sequence.addAll(deck.sequence);
	}

	/** 發出最上面一張牌 */
	public T deal() {
		Preconditions.checkState(amount() > 0);
		return sequence.remove(amount() - 1);
	}

	/** 發出最上面 N 張牌 */
	public Deck<T> deal(int amount) {
		Preconditions.checkState(amount() > amount);
		Deck<T> result = new Deck<>();

		for (int i = 0; i < amount; i++) {
			result.add(deal());
		}

		return result;
	}

	/**
	 * 取出從上面數起的第 N 張牌
	 *
	 * @param index 值域：1～{@link #amount()}
	 */
	public T take(int index) {
		Preconditions.checkState(amount() >= index);
		return sequence.remove(amount() - index);
	}

	/** 洗牌 */
	public Deck<T> shuffle() {
		Collections.shuffle(sequence);
		return this;
	}

	@Override
	public String toString() {
		return Lists.reverse(sequence).stream()
			.map(card -> card.toString())
			.collect(Collectors.joining(", "));
	}


	//// ======== 撲克牌專區 ======== ////
	/**
	 * @return 一副標準的撲克牌（沒有鬼牌）
	 */
	public static Deck<Card> standard() {
		return standard(false);
	}

	/**
	 * @param hasJoker 是否加入鬼牌
	 * @return 一副標準的撲克牌
	 */
	public static Deck<Card> standard(boolean hasJoker) {
		Deck<Card> result = new Deck<>();

		for (Suit s : Suit.values()) {
			if (Suit.joker == s) { continue; }

			for (int i = 1; i <= 13; i++) {
				result.add(new Card(s, i));
			}
		}

		if (hasJoker) {
			result.add(new Card(Suit.joker, 1));
			result.add(new Card(Suit.joker, 2));
		}

		return result;
	}
	//// ================ ////
}
