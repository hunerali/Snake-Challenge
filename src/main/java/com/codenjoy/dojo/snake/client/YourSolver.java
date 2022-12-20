package com.codenjoy.dojo.snake.client;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.RandomDice;
import com.codenjoy.dojo.snake.model.Elements;

import java.util.List;

/**
 * User: your name
 */
public class YourSolver implements Solver<Board> {

  private Dice dice;
  private Board board;
  private Point apple;
  private Point head;
  private Point stone;

  private List<Point> barriers;
  private List<Point> snake;

  public YourSolver(Dice dice) {
    this.dice = dice;
  }

  public void setUp(Board board) {
    this.board = board;
    this.apple = board.getApples().get(0);
    this.head = board.getHead();
    this.stone = board.getStones().get(0);
    this.snake = board.getSnake();
    this.barriers = board.getBarriers();

  }


  public Direction getDirection(Board board) {
    setUp(board);
    if (apple.getX() > head.getX()) {
      return checkBarriers(Direction.RIGHT);
      //return Direction.RIGHT;
    }

    if (apple.getX() < head.getX()) {
      return checkBarriers(Direction.LEFT);
      //return Direction.LEFT;
    }

    if (apple.getX() == head.getX() && apple.getY() < head.getY()) {
      return checkBarriers(Direction.DOWN);
      // return Direction.DOWN;
    }

    if (apple.getX() == head.getX() && apple.getY() > head.getY()) {
      return checkBarriers(Direction.UP);
      //return Direction.UP;

    }

    return null;
  }

  public Direction checkStone(Direction direction) {
    if (direction.change(head).equals(stone)) {
      if (direction.equals(Direction.UP) || direction.equals(Direction.DOWN)) {
        if (board.getBarriers().contains(Direction.RIGHT.change(head))) {
          return Direction.LEFT;
        }
        return Direction.RIGHT;
      }
      if (board.getBarriers().contains(Direction.UP.change(head))) {
        return Direction.DOWN;
      }
      return Direction.UP;
    }
    return direction;
  }

  public Direction checkBarriers(Direction direction) {
    if (barriers.contains(direction.change(head))) {
      if (direction.equals(Direction.UP)) {
        return checkBarriers(Direction.LEFT);
      }
      if (direction.equals(Direction.LEFT)) {
        return checkBarriers(Direction.DOWN);
      }
      if (direction.equals(Direction.RIGHT)) {
        return checkBarriers(Direction.UP);
      }
      if (direction.equals(Direction.DOWN)) {
        return checkBarriers(Direction.RIGHT);
      }
    }
    return direction;
  }

  @Override
  public String get(Board board) {
    this.board = board;
    System.out.println(board.toString());
    return getDirection(board).toString();
  }

  public static void main(String[] args) {
    WebSocketRunner.runClient(
        // paste here board page url from browser after registration
        "http://159.89.27.106/codenjoy-contest/board/player/svbccu3rgmdu7ku7a2jn?code=7341848737640090849",
        new YourSolver(new RandomDice()),
        new Board());
  }

}
