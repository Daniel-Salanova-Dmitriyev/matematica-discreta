import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.Set;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb el comentari "// TO DO".
 *
 * Cada tema té el mateix pes, i l'avaluació consistirà en:
 *
 * - Principalment, el correcte funcionament de cada mètode (provant amb diferents entrades). Teniu
 *   alguns exemples al mètode `main`.
 *
 * - La neteja del codi (pensau-ho com faltes d'ortografia). L'estàndar que heu de seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html . No és
 *   necessari seguir-la estrictament, però ens basarem en ella per jutjar si qualcuna se'n desvia
 *   molt.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni mètodes de classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 8.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1: Daniel Salanova Dmitriyev
 * - Nom 2:
 * - Nom 3:
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital abans de la data que se us hagui
 * comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més fàcilment
 * les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat, assegurau-vos
 * que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {

    /*
   * Aquí teniu els exercicis del Tema 1 (Lògica).
   *
   * Els mètodes reben de paràmetre l'univers (representat com un array) i els predicats adients
   * (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és un element de
   * l'univers, podeu fer-ho com `p.test(x)`, té com resultat un booleà. Els predicats de dues
   * variables són de tipus `BiPredicate<Integer, Integer>` i similarment s'avaluen com
   * `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis us demanam que donat l'univers i els predicats retorneu `true`
   * o `false` segons si la proposició donada és certa (suposau que l'univers és suficientment
   * petit com per utilitzar la força bruta)
     */
    static class Tema1 {

        /*
         * És cert que ∀x,y. P(x,y) -> Q(x) ^ R(y) ?
         */
        static boolean exercici1(int[] universe, BiPredicate<Integer, Integer> p, Predicate<Integer> q, Predicate<Integer> r) {
            for (int x = 0; x < universe.length; x++) {
                for (int y = 0; y < universe.length; y++) {
                    // !P(X,Y)||Q(X)&&R(Y) -> TRUE
                    if (!(!p.test(universe[x], universe[y]) || q.test(universe[x]) && r.test(universe[y]))) {
                        System.out.println("T1-1: false");
                        return false;
                    }
                }
            }
            System.out.println("T1-1: true");
            return true;
        }

        /*
         * És cert que ∃!x. ∀y. Q(y) -> P(x) ?
         */
        static boolean exercici2(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {

            for (int y = 0; y < universe.length; y++) {
                int vecesXCumplido = 0;
                for (int x = 0; x < universe.length; x++) {
                    //X solo se puede cumplir una vez, por ende, si se cumple mas de una
                    //vez la X no es unica
                    if (!q.test(universe[y]) || p.test(universe[x])) {
                        vecesXCumplido++;
                        if (vecesXCumplido >= 2) {
                            System.out.println("T1-2: falso");
                            return false;
                        }
                    }
                }
            }

            System.out.println("T1-2: Verdadero");
            return true;
        }

        /*
     * És cert que ¬(∃x. ∀y. y ⊆ x) ?
     *
     * Observau que els membres de l'univers són arrays, tractau-los com conjunts i podeu suposar
     * que cada un d'ells està ordenat de menor a major.
         */
        static boolean exercici3(int[][] universe) {
            //Resolviendo   ∀x, ∃y. y ¬⊆ x)
            int contador = 0; //contador de y que se cumplen con x

            for (int[] x : universe) {
                for (int[] y : universe) {

                    int c = 0; //Contador de incluidos
                    for (int i = 0; i < x.length; i++) {
                        for (int j = 0; j < y.length; j++) {
                            if (x[i] == y[j]) {
                                c++;
                            }
                        }
                    }

                    if (c != y.length) {
                        contador++;
                        break;
                    }

                }
            }
            if (contador == universe.length) {
                System.out.println("T1-3: true");
                return true;
            } else {
                System.out.println("T1-3: falso");
                return false;
            }

        }

        /*
     * És cert que ∀x. ∃!y. x·y ≡ 1 (mod n) ?
         */
        static boolean exercici4(int[] universe, int n) {

            for (int x = 0; x < universe.length; x++) {
                int c = 0;
                for (int y = 0; y < universe.length; y++) {
                    int a = universe[x] * universe[y];
                    int b = n;
                    if (a % b == 1) {
                        c++;
                    }

                }
                if (c != 1) {
                    System.out.println("T1-4 false");
                    return false;
                }

            }

            System.out.println("T1-4 true");
            return true;
        }

        static int mcd(int a, int b) {
            if (a == 0 || b == 0) {
                // Si a,b pertenencen a Z\{0}, siempre hay mcd
                return -1;
            }
            int g; // Numero mas grande 
            int p; // Numero mas peque�o
            if (a > b) {
                g = a;
                p = b;
            } else {
                g = b;
                p = a;
            }

            while (g != p) {
                if (g > p) {
                    g -= p;
                } else {
                    p -= g;
                }
            }
            return g;
        }

        /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
         */
        static void tests() {
            // Exercici 1
            // ?x,y. P(x,y) -> Q(x) ^ R(y)

            assertThat(
                    exercici1(
                            new int[]{2, 3, 5, 6},
                            (x, y) -> x * y <= 4,
                            x -> x <= 3,
                            x -> x <= 3
                    )
            );

            assertThat(
                    !exercici1(
                            new int[]{-2, -1, 0, 1, 2, 3},
                            (x, y) -> x * y >= 0,
                            x -> x >= 0,
                            x -> x >= 0
                    )
            );

            // Exercici 2
            // ?!x. ?y. Q(y) -> P(x) ?
            assertThat(
                    exercici2(
                            new int[]{-1, 1, 2, 3, 4},
                            x -> x < 0,
                            x -> true
                    )
            );

            assertThat(
                    !exercici2(
                            new int[]{1, 2, 3, 4, 5, 6},
                            x -> x % 2 == 0, // x �s m�ltiple de 2
                            x -> x % 4 == 0 // x �s m�ltiple de 4
                    )
            );

            // Exercici 3
            // �(?x. ?y. y ? x) ?
            assertThat(
                    exercici3(new int[][]{{1, 2}, {0, 3}, {1, 2, 3}, {}})
            );

            assertThat(
                    !exercici3(new int[][]{{1, 2}, {0, 3}, {1, 2, 3}, {}, {0, 1, 2, 3}})
            );

            // Exercici 4
            // �s cert que ?x. ?!y. x�y ? 1 (mod n) ?
            assertThat(
                    exercici4(
                            new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                            11
                    )
            );

            assertThat(
                    !exercici4(
                            new int[]{0, 5, 7},
                            13
                    )
            );
        }
    }

    /*
   * Aquí teniu els exercicis del Tema 2 (Conjunts).
   *
   * De la mateixa manera que al Tema 1, per senzillesa tractarem els conjunts com arrays (sense
   * elements repetits). Per tant, un conjunt de conjunts d'enters tendrà tipus int[][].
   *
   * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
   * només té dos elements. Per exemple
   *   int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
   * i també donarem el conjunt on està definida, per exemple
   *   int[] a = {0,1,2};
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam donant int[] a,
   * int[] b, i un objecte de tipus Function<Integer, Integer> que podeu avaluar com f.apply(x) (on
   * x és un enter d'a i el resultat f.apply(x) és un enter de b).
     */
    static class Tema2 {

        /*
     * És `p` una partició d'`a`?
     *
     * `p` és un array de conjunts, haureu de comprovar que siguin elements d'`a`. Podeu suposar que
     * tant `a` com cada un dels elements de `p` està ordenat de menor a major.
         */
        static boolean exercici1(int[] a, int[][] p) {
            boolean[] encontradas = new boolean[a.length];

            for (int[] particion : p) {

                for (int i = 0; i < a.length; i++) {
                    for (int j = 0; j < particion.length; j++) {
                        if (a[i] == particion[j] && !encontradas[i]) {
                            encontradas[i] = true;
                            break;
                        }
                    }
                }

            }

            // Comprobamos que se hayan encontrado todas
            for (int k = 0; k < encontradas.length; k++) {
                if (encontradas[k] == false) {
                    System.out.println("T2-1: Falso");
                    return false;
                }
            }

            System.out.println("T2-1: Verdadero");
            return true;
        }

        /*
     * Comprovau si la relació `rel` definida sobre `a` és un ordre parcial i que `x` n'és el mínim.
     *
     * Podeu soposar que `x` pertany a `a` i que `a` està ordenat de menor a major.
         */
        static boolean exercici2(int[] a, int[][] rel, int x) {
            return false; // TO DO
        }
        

        /*
     * Suposau que `f` és una funció amb domini `dom` i codomini `codom`.  Trobau l'antiimatge de
     * `y` (ordenau el resultat de menor a major, podeu utilitzar `Arrays.sort()`). Podeu suposar
     * que `y` pertany a `codom` i que tant `dom` com `codom` també estàn ordenats de menor a major.
         */
        static int[] exercici3(int[] dom, int[] codom, Function<Integer, Integer> f, int y) {
            ArrayList<Integer> antiimagen = new ArrayList<Integer>();

            for (int i = 0; i < dom.length; i++) {
                if (f.apply(dom[i]) == y) {
                    antiimagen.add(dom[i]);
                }
            }

            int[] resultado = new int[antiimagen.size()];
            for (int k = 0; k < antiimagen.size(); k++) {
                resultado[k] = antiimagen.get(k);
            }

            return resultado;
        }

        /*
     * Suposau que `f` és una funció amb domini `dom` i codomini `codom`.  Retornau:
     * - 3 si `f` és bijectiva
     * - 2 si `f` només és exhaustiva
     * - 1 si `f` només és injectiva
     * - 0 en qualsevol altre cas
     *
     * Podeu suposar que `dom` i `codom` estàn ordenats de menor a major. Per comoditat, podeu
     * utilitzar les constants definides a continuació:
         */
        static final int NOTHING_SPECIAL = 0;
        static final int INJECTIVE = 1;
        static final int SURJECTIVE = 2;
        static final int BIJECTIVE = INJECTIVE + SURJECTIVE;

        static int exercici4(int[] dom, int[] codom, Function<Integer, Integer> f) {
            boolean inyectiva = inyectiva(dom, codom, f);
            boolean exhaustiva = exhaustiva(dom, codom, f);

            if (inyectiva && exhaustiva) { //Biyectiva
                System.out.println("T2-4: Biyectiva");
                return 3;
            } else if (inyectiva) { //Inyectiva
                System.out.println("T2-4: Inyectiva");
                return 1;
            } else if (exhaustiva) { //Exhaustiva
                System.out.println("T2-4: Exhaustiva");
                return 2;
            }

            System.out.println("T2-4: Nada especial");
            return 0;
        }

        static boolean inyectiva(int[] dom, int codom[], Function<Integer, Integer> f) {
            boolean[] imagenes = new boolean[codom.length];

            for (int i = 0; i < dom.length; i++) {
                for (int j = 0; j < codom.length; j++) {
                    if (f.apply(dom[i]) == codom[j] && !imagenes[j]) {
                        imagenes[j] = true;
                        break;
                    } else if (f.apply(dom[i]) == codom[j] && imagenes[j]) {
                        return false;
                    }
                }
            }

            int c = 0;
            for (int k = 0; k < imagenes.length; k++) {
                if (imagenes[k]) {
                    c++;
                }
            }

            if (c == dom.length) {
                return true;
            }

            return false;
        }

        static boolean exhaustiva(int[] dom, int codom[], Function<Integer, Integer> f) {
            boolean[] imagenes = new boolean[codom.length];

            for (int i = 0; i < dom.length; i++) {
                for (int j = 0; j < codom.length; j++) {
                    if (f.apply(i) == codom[j]) {
                        imagenes[j] = true;
                    }
                }
            }

            int contador = 0;
            for (int k = 0; k < imagenes.length; k++) {
                if (imagenes[k]) {
                    contador++;
                }
            }

            if (contador == codom.length) {
                return true;
            }
            return false;
        }

        /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
         */
        static void tests() {
            // Exercici 1
            // `p` �s una partici� d'`a`?

            assertThat(
                    exercici1(
                            new int[]{1, 2, 3, 4, 5},
                            new int[][]{{1, 2}, {3, 5}, {4}}
                    )
            );

            assertThat(
                    !exercici1(
                            new int[]{1, 2, 3, 4, 5},
                            new int[][]{{1, 2}, {5}, {1, 4}}
                    )
            );

            // Exercici 2
            // �s `rel` definida sobre `a` d'ordre parcial i `x` n'�s el m�nim?
            ArrayList<int[]> divisibility = new ArrayList<int[]>();

            for (int i = 1; i < 8; i++) {
                for (int j = 1; j <= i; j++) {
                    if (i % j == 0) {
                        // i �s m�ltiple de j, �s a dir, j|i
                        divisibility.add(new int[]{j, i});
                    }
                }
            }

            assertThat(
                    exercici2(
                            new int[]{1, 2, 3, 4, 5, 6, 7},
                            divisibility.toArray(new int[][]{}),
                            1
                    )
            );

            assertThat(
                    !exercici2(
                            new int[]{1, 2, 3},
                            new int[][]{{1, 1}, {2, 2}, {3, 3}, {1, 2}, {2, 3}},
                            1
                    )
            );

            assertThat(
                    !exercici2(
                            new int[]{1, 2, 3, 4, 5, 6, 7},
                            divisibility.toArray(new int[][]{}),
                            2
                    )
            );
            // Exercici 3
            // calcular l'antiimatge de `y`
            assertThat(
                    Arrays.equals(
                            new int[]{0, 2},
                            exercici3(
                                    new int[]{0, 1, 2, 3},
                                    new int[]{0, 1},
                                    x -> x % 2, // residu de dividir entre 2
                                    0
                            )
                    )
            );

            assertThat(
                    Arrays.equals(
                            new int[]{},
                            exercici3(
                                    new int[]{0, 1, 2, 3},
                                    new int[]{0, 1, 2, 3, 4},
                                    x -> x + 1,
                                    0
                            )
                    )
            );

            // Exercici 4
            // classificar la funci� en res/injectiva/exhaustiva/bijectiva
            assertThat(
                    exercici4(
                            new int[]{0, 1, 2, 3},
                            new int[]{0, 1, 2, 3},
                            x -> (x + 1) % 4
                    )
                    == BIJECTIVE
            );

            assertThat(
                    exercici4(
                            new int[]{0, 1, 2, 3},
                            new int[]{0, 1, 2, 3, 4},
                            x -> x + 1
                    )
                    == INJECTIVE
            );

            assertThat(
                    exercici4(
                            new int[]{0, 1, 2, 3},
                            new int[]{0, 1},
                            x -> x / 2
                    )
                    == SURJECTIVE
            );

            assertThat(
                    exercici4(
                            new int[]{0, 1, 2, 3},
                            new int[]{0, 1, 2, 3},
                            x -> x <= 1 ? x + 1 : x - 1
                    )
                    == NOTHING_SPECIAL
            );
        }
    }

    /*
   * Aquí teniu els exercicis del Tema 3 (Aritmètica).
   *
     */
    static class Tema3 {

        /*
     * Donat `a`, `b` retornau el màxim comú divisor entre `a` i `b`.
     *
     * Podeu suposar que `a` i `b` són positius.
         */
        static int mcd(int a, int b) {
            int g; // Numero mas grande 
            int p; // Numero mas peque�o
            if (a > b) {
                g = a;
                p = b;
            } else {
                g = b;
                p = a;
            }

            while (g != p) {
                if (g > p) {
                    g -= p;
                } else {
                    p -= g;
                }
            }
            return g;
        }

        static int exercici1(int a, int b) {
            int mcd = mcd(a, b);
            System.out.println("T3-1:Minimo Comun Divisor: " + mcd);
            return mcd;
        }

        /*
     * Es cert que `a``x` + `b``y` = `c` té solució?.
     *
     * Podeu suposar que `a`, `b` i `c` són positius.
         */
        static boolean exercici2(int a, int b, int c) {
            //Solo tiene division cuando mcd(a,b)|c por tanto
            int mcd = mcd(a, b);

            if (c % mcd == 0) {
                //Tiene solucion
                System.out.println("T3-2:Ecuacion tiene solucion");
                return true;
            }
            //No tiene solucion
            System.out.println("T3-2:Ecuacion no tiene solucion");
            return false;
        }

        /*
     * Quin es l'invers de `a` mòdul `n`?
     *
     * Retornau l'invers sempre entre 1 i `n-1`, en cas que no existeixi retornau -1
         */
        static int exercici3(int a, int n) {
            // Solo existe inverso si mcd(a,n) = 1;
            if (mcd(a, n) == 1) {
                int g = 0; // grande
                int p = 0; // peque�o
                int ind = 0;
                if (a > n) {
                    g = a;
                    p = n;
                    ind = 0;
                } else {
                    g = n;
                    p = a;
                    ind = 1;
                }
                int[] euclides = euclidesAlgorithm(g, p);
                int sol = euclides[ind];
                if (sol < 0) {
                    sol += n;
                }
                System.out.println("T3-3: Inverso es: " + sol);
                return sol;
            }
            System.out.println("T3-3: No existe inverso");
            return -1;
        }

        static int[] euclidesAlgorithm(int a, int b) {
            int[] arr = new int[2]; // x y
            int r0 = a;//0 
            int r1 = b;//1

            int x0 = 1;
            int x1 = 0;
            int xi = 0;

            int y0 = 0;
            int y1 = 1;
            int yi = 0;

            while (r1 > 1) { // Paramos cuando estamos a 1
                int res = r0 % r1;
                int qi = divisionEntera(r0, r1);

                yi = y0 - qi * y1;
                y0 = y1;
                y1 = yi;

                xi = x0 - qi * x1;
                x0 = x1;
                x1 = xi;

                r0 = r1;
                r1 = res;
            }
            arr[0] = x1;
            arr[1] = y1;
            return arr;
        }

        static int divisionEntera(int a, int b) {
            int q = -1;
            int r = a % b; //resto
            q = (a - r) / b;
            return q;
        }

        /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
         */
        static void tests() {
            // Exercici 1
            // `mcd(a,b)`

            assertThat(
                    exercici1(2, 4) == 2
            );

            assertThat(
                    exercici1(1236, 984) == 12
            );

            // Exercici 2
            // `a``x` + `b``y` = `c` t� soluci�?
            assertThat(
                    exercici2(4, 2, 2)
            );
            assertThat(
                    !exercici2(6, 2, 1)
            );
            // Exercici 3
            // invers de `a` m�dul `n`
            assertThat(exercici3(2, 5) == 3);
            assertThat(exercici3(2, 6) == -1);
        }
    }

    static class Tema4 {

        /*
     * Donada una matriu d'adjacencia `A` d'un graf no dirigit, retornau l'ordre i la mida del graf.
         */
        static int[] exercici1(int[][] A) {
            // Usaremos la regla de las encaixades de mans 2|E| = Sumatoria d(u)
            // Cantidad de filas = misma cantidad de nodos en una matriz de adyacencia
            int V = A.length;
            int du = 0; //Sumatorio de incidencias

            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < A[0].length; j++) {
                    if (A[i][j] == 1) {
                        du++;
                    }
                }
            }

            int E = du / 2;
            System.out.println("T4-1 Orden: " + V);
            System.out.println("T4-1 Tama�o: " + E);

            int[] res = new int[2];
            res[0] = V;
            res[1] = E;
            return res;
        }

        /*
     * Donada una matriu d'adjacencia `A` d'un graf no dirigit, digau si el graf es euleri�.
         */
        static boolean exercici2(int[][] A) {
            // Para ver si es euleriano debe tener grado 2 todos los nodos del grafo.
            int nodosGrado2 = 0;
            for (int i = 0; i < A.length; i++) {
                int grado = 0;
                for (int j = 0; j < A[0].length; j++) {
                    if (A[i][j] == 1) {
                        grado++;
                    }
                }

                if (grado % 2 == 0) {
                    nodosGrado2++;
                }
            }

            if (nodosGrado2 == A.length) {
                System.out.println("T4-2: Es euleriano");
                return true;
            }

            System.out.println("T4-2: No es euleriano");
            return false; // TO DO
        }

        /*
     * Donat `n` el n�mero de fulles d'un arbre arrelat i `d` el nombre de fills dels nodes interiors i de l'arrel,
     * retornau el nombre total de v�rtexos de l'arbre
     *
         */
        static int exercici3(int n, int d) {
            // Las aristas de un �rbol son nodos interiores + hojas
            // Los suma de grados de nodos de un arbol son hojas + (d+1)nodos_interiores + d
            int aristas = 0;
            int numeroNodosInteriores = (-n + d) / (2 - d - 1);
            aristas = numeroNodosInteriores + n;

            // Por encajada de manos
            int vertices = aristas + 1;
            System.out.println("T4-3 Numero de vertices: " + vertices);
            return vertices;

        }

        /*
     * Donada una matriu d'adjacencia `A` d'un graf connex no dirigit, digau si el graf cont� alg�n cicle.
         */
        static boolean exercici4(int[][] A) {
            // Un grafo es ac�clico si y solo si, 
            // este es un arbol por tanto un arbol se caracter�za si |E| =  |v| - 1

            int v = A.length;
            int eArbol = v - 1;

            int eGrafo = 0;

            int du = 0; //Sumatorio de incidencias

            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < A[0].length; j++) {
                    if (A[i][j] == 1) {
                        du++;
                    }
                }
            }
            eGrafo = du / 2;

            if (eGrafo == eArbol) {
                // Es un arbol, por tanto no es ciclico
                System.out.println("T4-4 No es ciclico");
                return false;
            }

            System.out.println("T4-4 Es ciclico");
            return true;
        }

        /*
     * Aqu� teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
         */
        static void tests() {
            // Exercici 1
            // `ordre i mida`

            assertThat(
                    Arrays.equals(exercici1(new int[][]{{0, 1, 0}, {1, 0, 1}, {0, 1, 0}}), new int[]{3, 2})
            );

            assertThat(
                    Arrays.equals(exercici1(new int[][]{{0, 1, 0, 1}, {1, 0, 1, 1}, {0, 1, 0, 1}, {1, 1, 1, 0}}), new int[]{4, 5})
            );

            // Exercici 2
            // `Es euleri�?`
            assertThat(
                    exercici2(new int[][]{{0, 1, 1}, {1, 0, 1}, {1, 1, 0}})
            );
            assertThat(
                    !exercici2(new int[][]{{0, 1, 0}, {1, 0, 1}, {0, 1, 0}})
            );
            // Exercici 3
            // `Quants de nodes t� l'arbre?`
            assertThat(exercici3(5, 2) == 9);
            assertThat(exercici3(7, 3) == 10);

            // Exercici 4
            // `Cont� alg�n cicle?`
            assertThat(
                    exercici4(new int[][]{{0, 1, 1}, {1, 0, 1}, {1, 1, 0}})
            );
            assertThat(
                    !exercici4(new int[][]{{0, 1, 0}, {1, 0, 1}, {0, 1, 0}})
            );

        }
    }

    /*
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
     */
    public static void main(String[] args) {
        Tema1.tests();
        Tema2.tests();
        Tema3.tests();
        Tema4.tests();

    }

    static void assertThat(boolean b) {
        if (!b) {
            throw new AssertionError();
        }
    }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
