% Las cuatro operaciones reciben dos numeros y devuelven el resultado.
% number/1 valida los operandos; is/2 realiza el calculo aritmetico.
suma(A, B, R) :-
    number(A), number(B),
    R is A + B.

resta(A, B, R) :-
    number(A), number(B),
    R is A - B.

multiplicacion(A, B, R) :-
    number(A), number(B),
    R is A * B.

% La condicion evita evaluar una division entre cero.
division(A, B, R) :-
    number(A), number(B),
    B =\= 0,
    % El factor 1.0 conserva los decimales tambien en tuProlog.
    R is (A * 1.0) / B.
