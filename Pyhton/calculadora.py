import math;


def suma(a, b):
    return a + b
def resta(a, b):
    return a - b
def multiplicacion(a, b):
    return a * b
def division(a, b):
    if b != 0:
        return a / b
    else:
        return "Error: División por cero"
def potencia(a, b):
    return math.pow(a, b)
def raiz_cuadrada(a):
    if a >= 0:
        return math.sqrt(a)
    else:
        i=a
        return "Solucion en números complejos: "+str(complex(0,math.sqrt(-i)))
def raiz_cubica(a):
    return a ** (1/3)
def raiz_n(a, n):
    if n != 0:
        return a ** (1/n)
    else:
        return "Error: Raíz de índice cero"
def logaritmo(a, base=10):
    if a > 0 and base > 1:
        return math.log(a, base)
    else:
        return "Error: Logaritmo no definido para estos valores"
print("Bienvenido a la calculadora")
bandera=True;
while bandera:
    
    print("Seleccione la operación:")
    print("1. Suma")
    print("2. Resta")
    print("3. Multiplicación")
    print("4. División")
    print("5. Potencia")
    print("6. Raíz Cuadrada")
    print("7. Raíz Cúbica")
    print("8. Raíz enésima")
    print("9. Logaritmo\n")
    opcion = input()
    if opcion in ['1', '2', '3', '4', '5']:
        num1 = float(input("Ingrese el primer número: "))
        num2 = float(input("Ingrese el segundo número: "))
    if opcion == '1':
        print(f"{num1} + {num2} = {suma(num1, num2)}")
    elif opcion == '2':
        print(f"{num1} - {num2} = {resta(num1, num2)}")
    elif opcion == '3':
        print(f"{num1} * {num2} = {multiplicacion(num1, num2)}")
    elif opcion == '4':
        print(f"{num1} / {num2} = {division(num1, num2)}")
    elif opcion == '5':
        print(f"{num1} ^ {num2} = {potencia(num1, num2)}")
    elif opcion == '6':
        num = float(input("Ingrese el número: "))
        print(f"√{num} = {raiz_cuadrada(num)}")
    elif opcion == '7':
        num = float(input("Ingrese el número: "))
        print(f"∛{num} = {raiz_cubica(num)}")
    elif opcion == '8':
        num = float(input("Ingrese el número: "))
        n = float(input("Ingrese el índice de la raíz: "))
        print(f"{n}√{num} = {raiz_n(num, n)}")
    elif opcion == '9':
        num = float(input("Ingrese el número: "))
        base = float(input("Ingrese la base (default 10): ") or 10)
        print(f"log base {base} de {num} = {logaritmo(num, base)}")
    else:
        print("Opción inválida")
    respuesta = input("¿Desea realizar otra operación? (s/n): ")
    if respuesta.lower() != 's':
        bandera = False
        print("Gracias por usar la calculadora")
    else:
        print("\n")

    