/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nowe.presentacion;

import com.nowe.modelo.CDT;
import com.nowe.modelo.Movimiento;
import com.nowe.negocio.Cajero;
import com.nowe.persistencia.AccesoCuentasBancarias;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author Programacion
 */
public class MenuPrincipal {

    /**
     * @param args the command line arguments
     *
     *
     */
    //atributo
    private static Scanner consola = new Scanner(System.in);
    private static Cajero c1 = new Cajero();
    private static AccesoCuentasBancarias accesoBBDD = new AccesoCuentasBancarias();
    private static DecimalFormat f1 = new DecimalFormat( "#,##0.00");

    public static void main(String[] args) {
        //Crear el menú y recibir por consola la opción seleccionada
        //1. Declarar variables

        String opcion = null;
        int op = 0;
        while (true) {

            while (true) {
                //Menu  
                System.out.println("------ OPCIONES DEL CAJERO -----");
                System.out.println("1) Visualizar el saldo de una cuenta.");
                System.out.println("2) Visualizar el saldo de un CDT");
                System.out.println("3) Visualizar el saldo total de un cliente");
                System.out.println("4) Invertir un monto de dinero en un CDT");
                System.out.println("5) Cerrar la inversión en CDT");
                System.out.println("6) Ingresar un monto de dinero en una cuenta");
                System.out.println("7) Retirar un monto de dinero en una cuenta");
                System.out.println("8) Avanzar en un mes la simulación");
                System.out.println("9) Finalizar");
                //Validación

                System.out.println("Escriba la opción --> ");
                opcion = consola.next();
                //línea sensible a error
                try {
                    op = Integer.parseInt(opcion);
                } catch (NumberFormatException ex) {
                    continue;
                }
                if (op > 0 && op < 10) {
                    break;
                }
            }
            switch (op) {
                case 1:
                    consultarSaldo();
                    break;
                case 2:
                    consultarSaldoCDT();
                    break;
                case 3:
                    consultarSaldoTotal();
                    break;
                case 4:
                    AbrirCDT();
                    break;
                case 5:
                    cerrarCDT();
                    break;
                case 6:
                    ingreso();
                    break;
                case 7:
                    retiro();
                    break;
                case 8:
                    simulacion();
                    break;
                
            }
            if (op == 9) {
                break;
            }
        }

    }

    public static void consultarSaldo() {
        //Declarar variables

        int idcuenta = 0;
        System.out.println("Escriba el número de la cuenta para mostrar el saldo --> ");
        idcuenta = consola.nextInt();

        try {
            System.out.println("Saldo de la cuenta " + idcuenta + " --> " + f1.format(c1.consultaSaldo(idcuenta)));
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    //Método para consultar Sado CDT y obtener por pantalla el número de CDT
    public static void consultarSaldoCDT() {
        //1. Declarar variables
        int idInversion = 0;
        // 2. Solicitar el valor de la inversion por consola
        System.out.println("Escriba el id de la inversión CDT --> ");
        idInversion = consola.nextInt();
        try {
            System.out.println("Saldo de la CDT " + idInversion + " -> " + f1.format(c1.consultaSaldoCDT(idInversion)));
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
        }

    }

    private static void consultarSaldoTotal() {
        //1. Declarar variables
        String nif = null;
        //2. Solicitar el valor del nif
        System.out.println("Escriba el nif del cliente para ver su saldo total --> ");
        nif = consola.next();
        try {
            System.out.println("Saldo total del nif " + nif + " --> " + f1.format(c1.consultaSaldoTotal(nif)));
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    private static void AbrirCDT() {
        //1. Declaración de variables
        int idCuenta = 0;
        CDT inversion = null;
        double interesMensual = 0, montoInversion = 0;
        //Solicitar los datos de la inversión
        System.out.println("---- ALTA DE UN CDT -----");
        System.out.println("Escriba el id cuenta --> ");
        idCuenta = consola.nextInt();
        System.out.println("Escriba los intereses pactados con el cliente--> ");
        interesMensual = consola.nextDouble();
        System.out.println("Escriba el monto de la inversión --> ");
        montoInversion = consola.nextDouble();
        //2.Instanciar el CDT con los datos recibidos por consola       
        inversion = new CDT(idCuenta, interesMensual, montoInversion);
        try {
            //3. Llamar a la capa persistencia
            System.out.println("¿La inversión se ha dado de alta ? --> " + accesoBBDD.crearInversion(inversion));
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    private static void cerrarCDT() {
        //1. Declaración variables
        int idInversion;
        //2. Recibir la información de la consola
        System.out.println("------ CERRAR INVERSION ------");
        System.out.println("Escriba la inversión a cerrar ---> ");
        idInversion = consola.nextInt();
        try {
            System.out.println("¿Se ha cerrado la inversión con éxito ? --> " + accesoBBDD.cerrarInversion(idInversion));
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    private static void ingreso() {
        // 1. Solicitar los datos por consola   
        int idCuenta = 0;
        double cantidad = 0;
        Movimiento m1 = null;
        System.out.println("----- REALIZAR UN INGRESO -----");
        System.out.println("Escriba la cuenta --> ");
        idCuenta = consola.nextInt();
        System.out.println("Escriba la cantidad a ingresar --> ");
        cantidad = consola.nextDouble();

        // 2. Instanciar una clase movimiento con los datos recibidos
        m1 = new Movimiento(cantidad, idCuenta);

        try {
            // 3. Llamar a la capa de persistencia para ejecutar el método de ingreso
            System.out.println("¿Se ha realizado el ingreso? -->" + accesoBBDD.Ingreso(m1));
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    private static void retiro() {
        // 1. Solicitar los datos por consola
        int idCuenta = 0;
        double cantidad = 0;
        Movimiento m1 = null;
        System.out.println("----- REALIZAR UN RETIRO -----");
        System.out.println("Escriba la cuenta --> ");
        idCuenta = consola.nextInt();
        System.out.println("Escriba la cantidad a retirar --> ");
        cantidad = consola.nextDouble();
        // 2. Instanciar una clase movimiento con los datos recibidos       
        m1 = new Movimiento(cantidad, idCuenta);
        try {
            // 3. Llamar a la capa de persistencia para ejecutar el método de ingreso
            System.out.println("¿Se ha realizado el reitro? -->" + accesoBBDD.retiro(m1));
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    private static void simulacion() {
        // 1. Solicitar los datos por consola
        String nif = null;
        System.out.println("----- REALIZAR LA SIMULACION  -----");
        System.out.println("Escriba el NIF  --> ");
        nif = consola.next();
        try {
            // 3. Llamar a la capa de persistencia para ejecutar el método de ingreso
            System.out.println("El saldo simulado al final del mes es  -->" + f1.format(accesoBBDD.simulacion(nif)));
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
        }
    }
}
