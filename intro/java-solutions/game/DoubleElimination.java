package game;

import java.util.*;

public class DoubleElimination {
    static int round, currentLow = 0, n, m, p, k, amount, active, num, pl1, pl2, tab, actualLow;
    static boolean human, log;
    static List<Integer> upperNums;
    static List<Integer> lowerNums;
    static Map<Integer, Participant> players;
    static List<int[]> finalTab;

    public static void initgame() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter m, n, k and p:");
        m = sc.nextInt();
        n = sc.nextInt();
        k = sc.nextInt();
        p = sc.nextInt();
        Random rd = new Random();
        human = false;
        System.out.println("Enter the number of players:");
        num = 0;
        amount = sc.nextInt();
        players = new LinkedHashMap<>();
        System.out.println("Do you want to participate? Enter Y/N");
        active = (int) Math.pow(2, (int) (Math.log10(amount) / Math.log10(2)));
        while (true) {
            String answer = sc.next();
            if (answer.equals("Y")) {
                human = true;
                num = rd.nextInt(active - 1);
                System.out.println("You were assigned " + (num + 1));
                break;
            } else if (answer.equals("N")) {
                break;
            } else {
                System.out.println("...");
            }
        }
        System.out.println("Do you want to see the log? Enter Y/N");
        while (true) {
            String answer = sc.next();
            if (answer.equals("Y")) {
                log = true;
                break;
            } else if (answer.equals("N")) {
                log = false;
                break;
            } else {
                System.out.println("...");
            }
        }
        upperNums = new ArrayList<>();
        lowerNums = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            if (human && i == num) {
                players.put(i, new Participant(new HumanPlayer()));
            } else {
                players.put(i, new Participant(new RandomPlayer(m, n)));
            }
            upperNums.add(i);
        }
        finalTab = new ArrayList<>();
        pl1 = 0;
        pl2 = 0;
        tab = amount;
        actualLow = 0;
    }

    public static int[][] draw(List<Integer> nums, int size) {
        int j = 0;
        int[][] res = new int[size / 2][2];
        int depth = 0;
        while (j < size / 2) {
            pl1 = nums.get(2 * j);
            pl2 = nums.get(2 * j + 1);
            if (players.get(pl1).getPrevOpponent() != players.get(pl2).getPrevOpponent() || size == 2 || depth >= 100) {
                res[j][0] = pl1;
                res[j][1] = pl2;
                j++;
            } else {
                Collections.shuffle(nums);
                j = 0;
                res = new int[size / 2][2];
            }
            depth++;
        }
        return res;
    }

    public static List<Integer>[] games(int[][] pairs, List<Integer> nums, int size) {
        List<Integer>[] res = new List[2];
        res[0] = nums;
        res[1] = new ArrayList<>();
        int j = 0;
        while (j < size / 2) {
            System.out.println("Game " + (j + 1));
            pl1 = pairs[j][0];
            pl2 = pairs[j][1];
            Participant p1 = players.get(pl1);
            p1.assignPrevOpp(pl2);
            players.put(pl1, p1);
            Participant p2 = players.get(pl2);
            p2.assignPrevOpp(pl1);
            players.put(pl2, p2);
            while (true) {
                Game game = new Game(log, players.get(pl1).getType(), players.get(pl2).getType());
                int result = game.play(new MNKBoard(m, n, k, p));
                if (result == 1) {
                    res[0].remove((Integer) pl2);
                    res[1].add(pl2);
                    break;
                } else if (result == 2) {
                    res[0].remove((Integer) pl1);
                    res[1].add(pl1);
                    break;
                }
            }
            j++;
        }
        return res;
    }

    public static void finalle() {
        System.out.println("======GRAND FINALLE======");
        finalTab.add(new int[1]);
        finalTab.add(new int[1]);
        while (true) {
            Game game = new Game(false, players.get(upperNums.getFirst()).getType(), players.get(lowerNums.getFirst()).getType());
            int result = game.play(new MNKBoard(m, n, k, p));
            if (result == 1) {
                finalTab.get(round - 2)[0] = lowerNums.getFirst();
                finalTab.get(round - 1)[0] = upperNums.getFirst();
                break;
            } else if (result == 2) {
                finalTab.get(round - 2)[0] = lowerNums.getFirst();
                finalTab.get(round - 1)[0] = upperNums.getFirst();
                break;
            }
        }
        System.out.println("End of tournament");
    }

    public static void results() {
        System.out.println("===============RESULTS===============");
        for (int i = finalTab.size() - 1; i >= 0; i--) {
            for (int j = 0; j < finalTab.get(i).length; j++) {
                if (finalTab.get(i)[j] == num && human) {
                    System.out.print("Player " + (num + 1) + "(You) ");
                } else {
                    System.out.print("Player " + (finalTab.get(i)[j] + 1) + "(Bot) ");
                }
            }
            System.out.println("placed " + (finalTab.size() - i));
        }
    }

    public static int getActive(List<Integer> nums) {
        int result;
        if (!nums.isEmpty()) {
            result = (int) Math.pow(2, (int) (Math.log10(nums.size()) / Math.log10(2)));
        } else {
            result = 0;
        }
        return result;
    }

    public static void main(String[] args) {
        initgame();
        while (true) {
            round++;
            if (upperNums.size() != 1 || lowerNums.size() != 1) {
                System.out.println("======Round " + round + "======");
                if (upperNums.size() > 1) {
                    System.out.println("Upper Standings games");
                }
                active = getActive(upperNums);
                Collections.shuffle(upperNums);
                List<Integer>[] temp = games(draw(upperNums, active), upperNums, active);
                upperNums = temp[0];
                lowerNums.addAll(temp[1]);
                currentLow = actualLow;
                actualLow = getActive(lowerNums);
                currentLow = Math.min(currentLow, actualLow);
                Collections.shuffle(lowerNums);
                if (round != 1) {
                    System.out.println("Lower Standings games");
                    finalTab.add(new int[currentLow / 2]);
                }
                temp = games(draw(lowerNums, currentLow), lowerNums, currentLow);
                lowerNums = temp[0];
                for (int i = 0; i < currentLow / 2; i++) {
                    finalTab.get(round - 2)[i] = temp[1].get(i);
                }
            } else {
                finalle();
                break;
            }
        }
        results();
    }
}
