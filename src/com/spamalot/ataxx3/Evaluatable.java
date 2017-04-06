package com.spamalot.ataxx3;

public interface Evaluatable {

  int getEvaluation();

  void setEvaluation(int evaluation);

  int compareTo(Evaluatable o);
}
