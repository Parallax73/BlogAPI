create table if not exists Usuarios(
    usuario_Id int not null auto_increment,
    nome varchar(100),
    senha varchar(100),

    primary key (usuario_Id)
);

create table if not exists Posts(
    post_Id int not null auto_increment,
    texto varchar(500) not null,
    dia datetime not null,
    usuario_Id int not null,

    primary key (post_Id),
    foreign key (usuario_Id) references Usuarios(usuario_Id)

);