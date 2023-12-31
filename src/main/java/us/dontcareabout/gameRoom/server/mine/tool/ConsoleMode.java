package us.dontcareabout.gameRoom.server.mine.tool;

import static us.dontcareabout.gameRoom.console.util.Input.$int;

import java.util.Arrays;

import us.dontcareabout.gameRoom.client.mine.MineGM;
import us.dontcareabout.gameRoom.client.mine.ai.DummyAI;
import us.dontcareabout.gameRoom.client.mine.ai.JavaAI;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.Result;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

/**
 * console mode 的玩家先後順序是寫死的（因為懶），
 * 但是可以改變 {@link #ai} 的值來設定要用那一個 AI。
 */
//Refactory 如果有辦法提煉出 console 的共用架構，改寫的時候改放到 /console 底下
public class ConsoleMode {
	private static final String playerId = "Player";

	private static JavaAI ai = new DummyAI();

	public static void main(String[] args) {
		MineGM gm = new MineGM(Arrays.asList(playerId, ai.name()));
		GameInfo result;
		XY xy;

		do {
			do {
				result = gm.getGameInfo();
				if(result.getPlayerHit()[0] >= (result.getTotal() / 2.0)) {
					System.out.println("Player win!");
					System.exit(0);	//暴力結束 \囧/
				}
				show(result);
				xy = new XY(
					$int("x (0~" + (result.getWidth() - 1) + ") : "),
					$int("y (0~" + (result.getHeight() - 1) + ") : ")
				);
			} while(gm.shoot(playerId, xy) != Result.miss);

			do {
				xy = ai.guess(gm.getGameInfo());
			} while(gm.shoot(ai.name(), xy) == Result.hit);

			//TODO 顯示 Player 踩了哪些
			System.out.println("\n========================");
		} while(result.getPlayerHit()[1] < (result.getTotal() / 2.0));

		System.out.println(ai.name() + " win!");
		System.exit(0);
	}

	public static void show(GameInfo info) {
		//畫 x 軸的 index
		System.out.print("  ");

		for(int x = 0; x < info.getWidth(); x++) {
			System.out.print((x % 10) + " ");
		}

		System.out.println();

		//畫邊框
		System.out.print(" +");

		for(int x = 0; x < info.getWidth(); x++) {
			System.out.print("--");
		}

		System.out.println();

		for(int y = 0; y < info.getHeight(); y++) {
			for(int x = 0; x < info.getWidth(); x++) {
				//畫 y 軸 index
				if(x == 0){
					System.out.print((y % 10)+"|");
				}

				switch(info.getMap()[x][y]) {
				case -1:
					System.out.print("-");
					break;
				case 0:
					System.out.print(" ");
					break;
				case MineGM.IS_MINE:
				case MineGM.P2_FLAG:
					System.out.print("*");
					break;
				default:
					System.out.print(info.getMap()[x][y]);
				}
				System.out.print(" ");
			}

			switch (y) {
			case 0:
				System.out.print("  Player : ");
				System.out.print(info.getPlayerHit()[0]);
				break;
			case 1:
				System.out.print("  " + ai.name() + " : ");
				System.out.print(info.getPlayerHit()[1]);
				break;
			case 2:
				System.out.print("  Remainder : ");
				System.out.print(info.getRemainder()+"/"+info.getTotal());
				break;
			default:
				break;
			}
			System.out.println();
		}
		System.out.println("========================");
	}
}
