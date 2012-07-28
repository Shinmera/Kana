/**********************\
  file: Answer.java
  package: kana
  author: Shinmera
  team: NexT
  license: GPL
  
  This file is part of Kana.

    Kana is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Kana is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Kana.  If not, see http://www.gnu.org/licenses/gpl-3.0.txt.
 */

package kana;

public class Answer {
    public static final int ANSWER_RIGHT = 0;
    public static final int ANSWER_WRONG = 1;
    public static final int ANSWER_NO_MORE = 2;
    public static final int ANSWER_NEXT = 3;
    public static final int ANSWER_ERROR = -1;
    
    public static final Answer NO_MORE = new Answer(ANSWER_NO_MORE,"-");
    public static final Answer ERROR = new Answer(ANSWER_ERROR,"ERR");
    
    private int answer;
    private Symbol correct;
    
    public Answer(int answer){this(answer,"");}
    public Answer(int answer,String correct){this(answer,new Symbol(correct));}
    public Answer(int answer,Symbol correct){this.answer=answer;this.correct=correct;}
    public Symbol getCorrect(){return correct;}
    public int getAnswer(){return answer;}
}
