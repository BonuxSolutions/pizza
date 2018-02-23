CREATE OR REPLACE PACKAGE PIZZA_PKG AS 

    type t_matrix is table of varchar2(1) index by varchar2(200);
    
    procedure mainp;

END PIZZA_PKG;
/


CREATE OR REPLACE PACKAGE BODY pizza_pkg AS

    r          NUMBER := 1000;
    c          NUMBER := 1000;
    l          NUMBER := 1; -- atleast L cells of each ingredient
    h          NUMBER := 6; -- max cells per slice 
    t          NUMBER;
    m          NUMBER;
  --------------------------------------
    rowc       VARCHAR2(32000);
    colr       VARCHAR2(32000) := '   ';
    colh       VARCHAR2(32000);
    matrica    t_matrix;
    res_matr   t_matrix;
    x_value    NUMBER;
    y_value    NUMBER;
    i          NUMBER := 0;
    retry      NUMBER := 0;
    try_c      NUMBER := 3;
    is_set_i   NUMBER := 0;
    rc_coord   VARCHAR2(32000);
    cur_xy     VARCHAR2(32000);
    nxt_xy     VARCHAR2(32000);
    slices     NUMBER := 0;

    PROCEDURE output_matrica (
        p_matrica IN t_matrix
    )
        AS
    BEGIN
        dbms_output.new_line;
        FOR y IN 0..r - 1 LOOP
            rowc := y
            || ' |';
            FOR x IN 0..c - 1 LOOP
                rc_coord := TO_CHAR(x,'FM0000')
                || '-'
                || TO_CHAR(y,'FM0000');

                rowc := rowc
                || p_matrica(rc_coord)
                || '|';
                IF
                    colh IS NULL
                THEN
                    colr := colr
                    || x
                    || ' ';
                END IF;

            END LOOP;

            IF
                colh IS NULL
            THEN
                colh := colr;
                dbms_output.put_line(colh);
            END IF;

            dbms_output.put_line(rowc);
        END LOOP;

        dbms_output.new_line;
    END output_matrica;

    PROCEDURE calc_ingredients AS

        d   NUMBER := dbms_random.value(low => least(l,h),high => l * h);
    BEGIN
        dbms_output.put_line('d='
        || d);
        t := round( (r * c) * (d / (l * h) ),0);

        m := ( r * c ) - t;
        dbms_output.put_line('t='
        || t
        || '; m='
        || m);
        matrica.DELETE;
        WHILE is_set_i < t LOOP
            x_value := round(dbms_random.value(low => 0,high => c - 1),0);

            y_value := round(dbms_random.value(low => 0,high => r - 1),0);

            rc_coord := TO_CHAR(x_value,'FM0000')
            || '-'
            || TO_CHAR(y_value,'FM0000');

            IF
                NOT matrica.EXISTS(rc_coord)
            THEN
                matrica(rc_coord) := 't';
                is_set_i := is_set_i + 1;
        --dbms_output.put_line(to_char(y_value || x_value, 'FM00')||';   x_value = ' || x_value || '; y_value = ' ||y_value);
            END IF;

            i := i + 1;
            IF
                i > greatest( (r * c),1000000) * 100 AND is_set_i < t
            THEN
                try_c := try_c + 1;
                i := 0;
            END IF;

            EXIT WHEN retry > try_c OR is_set_i >= t;
        END LOOP;

        dbms_output.put_line('matrica.count = '
        || matrica.count
        || ';    i = '
        || i
        || ';    is_set_i = '
        || is_set_i);

        i := 0;
        is_set_i := 0;
        try_c := 0;
        WHILE is_set_i < m LOOP
            x_value := round(dbms_random.value(low => 0,high => c - 1),0);

            y_value := round(dbms_random.value(low => 0,high => r - 1),0);

            rc_coord := TO_CHAR(x_value,'FM0000')
            || '-'
            || TO_CHAR(y_value,'FM0000');

            IF
                NOT matrica.EXISTS(rc_coord)
            THEN
                matrica(rc_coord) := 'm';
                is_set_i := is_set_i + 1;
        --dbms_output.put_line(to_char(x_value || y_value, 'FM00')||';x_value = ' || x_value || '; y_value = ' ||y_value);
            END IF;

            i := i + 1;
            IF
                i > greatest( (r * c),1000000) * 100 AND is_set_i < m
            THEN
                try_c := try_c + 1;
                i := 0;
            END IF;

            EXIT WHEN retry > try_c OR is_set_i >= t;
        END LOOP;

        dbms_output.put_line('matrica.count = '
        || matrica.count
        || ';    i = '
        || i
        || ';    is_set_i = '
        || is_set_i);

    END calc_ingredients;

    PROCEDURE mainp
        AS
    BEGIN
        calc_ingredients;
  --return;
        output_matrica(matrica);
  --------------------------------------------------
        res_matr := matrica;
        slices := 0;
  ---  can be given l and h
        FOR x IN 0..c - 1 LOOP
            FOR y IN 0..r - 1 LOOP
                cur_xy := TO_CHAR(x,'FM0000')
                || '-'
                || TO_CHAR(y,'FM0000');

                nxt_xy := TO_CHAR(x,'FM0000')
                || '-'
                || TO_CHAR(y + 1,'FM0000');

                IF
                    matrica.EXISTS(nxt_xy)
                THEN
                    IF
                        matrica(cur_xy) != matrica(nxt_xy)
                    THEN
                        slices := slices + 1;
                        res_matr(cur_xy) := '0';
                        res_matr(nxt_xy) := '0';
                    END IF;
                END IF;

            END LOOP;
        END LOOP;
--  output_matrica(res_matr);

        res_matr := matrica;
        slices := 0;
        FOR y IN 0..r - 1 LOOP
            FOR x IN 0..c - 1 LOOP
                cur_xy := TO_CHAR(x,'FM0000')
                || '-'
                || TO_CHAR(y,'FM0000');

                nxt_xy := TO_CHAR(x + 1,'FM0000')
                || '-'
                || TO_CHAR(y,'FM0000');

                IF
                    matrica.EXISTS(nxt_xy)
                THEN
                    IF
                        matrica(cur_xy) != matrica(nxt_xy)
                    THEN
                        slices := slices + 1;
                        res_matr(cur_xy) := '0';
                        res_matr(nxt_xy) := '0';
                    END IF;
                END IF;

            END LOOP;
        END LOOP;

        dbms_output.put_line('slices by 2 cells ver = '
        || slices);
        dbms_output.put_line('slices by 2 cells hor = '
        || slices);
--  output_matrica(res_matr);
    END mainp;

END pizza_pkg;
/
