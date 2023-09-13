package us.dontcareabout.gameRoom.server.mine.tool;

import java.util.Scanner;

import us.dontcareabout.gameRoom.client.mine.MineGM;
import us.dontcareabout.gameRoom.client.mine.Player;
import us.dontcareabout.gameRoom.client.mine.ai.DummyAI;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;

public class ConsoleMode {
	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		Player ai = new DummyAI();	//FIXME change your Player here!!

		MineGM gm = new MineGM();
		GameInfo result;
		int[] xy;

		do {
			do {
				result = MineGM.toGameInfo(gm);
				if(result.getPlayerHit()[0] >= (result.getTotal() / 2.0)) {
					System.out.println("Player win!");
					System.exit(0);	//暴力結束 \囧/
				}
				show(result);
			} while(gm.shoot(read("x (0~" + (result.getWidth() - 1) + ") : "),
					read("y (0~" + (result.getHeight() - 1) + ") : "), MineGM.PLAYER_1));

			gm.cleanTrace();

			do {
				xy = new int[2];
				ai.guess(MineGM.toGameInfo(gm), xy);
				gm.addTrace(xy);
			} while(gm.shoot(xy[0], xy[1], MineGM.PLAYER_2));

			//顯示 Player 踩了哪些
			result = MineGM.toGameInfo(gm);
			System.out.print("Player : ");

			for (int[] trace : result.getTrace()) {
				System.out.print("(" + trace[0] + "," + trace[1] + "), ");
			}

			System.out.println("\n========================");
		} while(result.getPlayerHit()[1] < (result.getTotal() / 2.0));

		System.out.println("Player win!");
		System.exit(0);
	}

	private static int read(String string) {
		System.out.print(string);
		try{
			return scanner.nextInt();
		}catch(Exception e){
			return -1;
		}
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
				case 9:
				case -9:
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
				System.out.print("  Player : ");
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
