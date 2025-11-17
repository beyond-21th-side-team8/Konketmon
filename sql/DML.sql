INSERT INTO konketmon (name, ascii_art, hp) VALUES ('꼬리선', '      /\\_/\\
     / o.o \\
    (   "   )
     \\     /
      ''---''
       \\ /
       ( )
        V',100);


INSERT INTO konketmon (name, ascii_art, hp)
VALUES ('안농', '     ,--.
    /    \\
    \\ () /
     `--''
       |
       |
      ( )',100);

INSERT INTO konketmon (name, ascii_art, hp)
VALUES ('잠만보', ' ,-.   ,-.
 /  `-\'\'    \
(  (O) (O)  )
 \_  `-\'\'  _/
 / `-----\'\' \
(           )
 \         /
  `-------\'\'',100);
INSERT INTO konketmon(name, ascii_art, hp)
VALUES ('버터플',
        '      .   .
           .-. \ /.-.
           (   \_/   )
            \__( ) __/
             \ (_) /', 100),
       ('디그다',
        '             ...
                  .       .
                --|  | |  |-------
               -  | (   ) |   --
                 -|       |--
             --------------------', 100),
       ('잉어킹',
        '       /`·.¸
              /¸...¸`:·
         ¸.·´  ¸   `·.¸.·´)
        : © ):´;      ¸  {
         `·.¸ `·  ¸.·´\`·¸)
             `\\´´\¸.·´', 100);


INSERT INTO konketmon (name, ascii_art, hp) VALUES ('이브이',
                                                    '
                                                           /\   /\
                                                          /  \_/  \
                                                         |   O O   |
                                                          \   >   /
                                                           `-----`
                                                          /`-----`\
                                                         |         |
                                                         |  _   _  |  .--.
                                                         | | | | | | /    \
                                                          \ \_|_/ /  ------
                                                           `-----` ',100);

INSERT INTO konketmon (name, ascii_art, hp) VALUES ('꼬마돌',
                                                    '         _______
                                                           .-/       `-.
                                                          /             \
                                                         |  ()       ()  |
                                                        (|   .  ____  .  |)
                                                          \   \______//  /
                                                           `-._______.-/
                                                            __||___||__
                                                       .--=|===========|--.
                                                      :                     :
                                                     : _____________________ :',100);

INSERT INTO konketmon (name, ascii_art, hp) VALUES ('이상해씨',
                                                    '
                                                                   .----------- ,.
                                                                ,-/ /        _, |
                                                        .   /     /             |
                                                      __|  |     |        \ `.  |
                                                    .|  _|  |     |    .  \  |  |
                                                    ( |  (_|  |     |     /|   .|
                                                    `--.    |     |     .|   |  |
                                                       |    |     |      |   |  |
                                                        \  /  \  /        \ / \ /',100);




-- 콘켓몬 생성 스크립트

# // 1번은 회원 가입 보여주기
INSERT INTO user
values (2, 2, 10, 0);
INSERT INTO user
values (3, 3, 60, 0);

INSERT INTO konketdex
values (null, 2, 1),
       (null, 2, 3),
       (null, 3, 1),
       (null, 3, 2),
       (null, 3, 3),
       (null, 3, 4),
       (null, 3, 5),
       (null, 3, 6),
       (null, 3, 7),
       (null, 3, 8),
       (null, 3, 9)