# mt2
Построение ручного парсера для логических выражений
Отчет по домашнему заданию № 2
Вариант 4

1. Разработка грамматики.

E → TE`

E` → |TE`| ^TE`| eps

T → AT`

T`  → &AT`| eps

N → A | !A

A → (E) | l



где l — маленькие латинские буквы

E — expression

E` - continue of expression

T — term

T` - continue of term

N — A or !A

A — atom

2. Построение лексического анализатора

Терминал	Токен

(	        LPAREN

)       	RPAREN

$	        END

^	        XOR

&	        AND

|	        OR

!	        NOT

a .. z	  NAME


3. Построение синтаксического анализатора

Нетерминал	FIRST	      FOLLOW

E	          !, (, l	    $, )

E`        	Eps, |, ^	  $, )

T	          !, (, l	    |, ^, $, )

T`        	Eps, &    	|, ^, $, )

N	          !, (, l	    &, |, ^, $, )

A	          (, l      	&, |, ^, $, )


4. См. Класс Visualisator




5. Подготовка набора тестов

Корректные тесты:
 a
 
 !a
 
 z | x
 
 y & p
 
 (p)
 
 p ^ q
 
 a     &     b
 
 q & (a ^ b) | ! a & ! (a | b | (d & c))



Ошибки на стадии лексического анализа:


manyletters & false //проверка, что переменные — одна буква

A | B //проверка, что переменные —  строчная буква

a * b + d //некорректный символ в выражении 

Ошибки на стадии синтаксического анализа:

((a & s)))( //неправильная скобочная последовательность — лишняя открывающаяся скобка

a & | b //несколько бинарных операндов без аргументов подряд

b (& a) //некорректная расстановка скобок

a b //отсутствует оператор между операндами

((a & s))(a) //отсутствует оператор между выражениями, заключенными в скобках

//пустая строка

()	//некорректное выражение внутри скобок

(a & b) ( //неправильная скобочная последовательность, лишняя открывающаяся скобка





