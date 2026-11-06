package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
interface Board {
    Position getPosition();
    Cell getCell();
    Result makeMove(Move move);
}
