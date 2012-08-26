/**********************\
  file: Console.java
  package: interfaces
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

package interfaces;

import kana.Kana;

public class Console implements Interface{
    private Kana main;
    private java.io.Console cons;
    //TODO: Actually implement this interface
    public Console(Kana main){
        this.main=main;
        cons = System.console();
    }

    public void update() {}

    public void initDone() {
    }

}
