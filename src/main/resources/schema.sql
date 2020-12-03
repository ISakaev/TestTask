CREATE TABLE IF NOT EXISTS Record (
    id                   INTEGER         NOT NULL        COMMENT 'Уникальный идентификатор' PRIMARY KEY AUTO_INCREMENT,
    version              INTEGER         NOT NULL        COMMENT 'Служебное поле hibernate',
    name                 VARCHAR(50)     NOT NULL        COMMENT 'Имя посетителя',
    date                 VARCHAR(50)     NOT NULL        COMMENT 'Дата занятия',
    phone                VARCHAR(150)    NOT NULL        COMMENT 'Номер телефона'
);
COMMENT ON TABLE Record IS 'Запись';
