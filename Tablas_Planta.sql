CREATE DATABASE PlantaN;
USE PlantaN;

Create TABLE TiposDPago(
	IdTipo int NOT NULL,
    NombreTipo nvarchar(30) NOT NULL,

	PRIMARY KEY(IdTipo)
);

Create TABLE DiasDSemana(
	NumDia int NOT NULL,
    NombreDia nvarchar(30) NOT NULL,

	PRIMARY KEY(NumDia)
);

Create TABLE MesesDAneo(
	NumMes int NOT NULL,
    NombreMes nvarchar(30) NOT NULL,

	PRIMARY KEY(NumMes)
);

CREATE TABLE Departamento(
    IdDepartamento int auto_increment,
    Nombre nvarchar(100) not null,

    PRIMARY KEY(IdDepartamento)
);

CREATE TABLE NombreTipos(
    IdTipo int auto_increment,
    NombreTipo nvarchar(100) not null,

    PRIMARY KEY(IdTipo)
);


Create TABLE Calendario(
	IdCalendario int auto_increment NOT NULL,
    NombreCalendario nvarchar(30) NOT NULL,
	TipoDPago int NOT NULL,

	PRIMARY KEY(IdCalendario),
    CONSTRAINT FK_TiposDPago_Calendario FOREIGN KEY (TipoDPago) REFERENCES TiposDPago(IdTipo) 
);




CREATE TABLE TiposEmpleados(
    IdTipo int auto_increment,
    IdCalendario int, -- fk a calendario
    CostoHora decimal(21,6) not null,
    CostoHoraExtra decimal(21,6) not null,
    CostoHoraDoble decimal(21,6) not null,

    PRIMARY KEY(IdTipo),
	CONSTRAINT FK_Calendario FOREIGN KEY (IdCalendario) REFERENCES Calendario(IdCalendario),
    CONSTRAINT FK_NombreTipos_TiposEmpleados FOREIGN KEY (IdTipo) REFERENCES NombreTipos(IdTipo)
);


Create TABLE diasdpago(
    ID INT auto_increment NOT NULL,
    IdTipoDPago int NOT NULL,
    FechaDia int NOT NULL,
    NumMes int NOT NULL,

    PRIMARY KEY(ID),
    CONSTRAINT FK_TipoDPago_DiasDPago FOREIGN KEY (IdTipoDPago) REFERENCES tiposdpago(IdTipo)
);

Create TABLE DiasTrabajados(
	IdCalendario INT NOT NULL,
    NumDia int NOT NULL,
	HoraEntrada time NOT NULL,
    HoraSalida time NOT NULL,
    CHorasLaborables int NOT NULL,
	
	PRIMARY KEY(IdCalendario, NumDia),
    CONSTRAINT FK_NumDia_DiasTrabajados FOREIGN KEY (NumDia) REFERENCES DiasDSemana(NumDia),
    CONSTRAINT FK_Calendario_DiasTrabajados FOREIGN KEY (IdCalendario) REFERENCES Calendario(IdCalendario)
);

Create Table DiasFeriados(
	ID int auto_increment NOT NULL,
    IdCalendario int NOT NULL,
	FechaDia int NOT NULL,
    NumMes int NOT NULL,
    TipoPago char NOT NULL, -- 'N' normal, 'D' Doble
    
    PRIMARY KEY(ID),
    CONSTRAINT FK_Calendario_DiasFeriados FOREIGN KEY (IdCalendario) REFERENCES Calendario(IdCalendario)
);

CREATE TABLE Empleados(
    IdEmpleado int auto_increment, -- luego se cambia a auto_increment
    Nombre nvarchar(50) not null,
    Apellidos nvarchar(100) not null,
    FechaIngreso date not null,
    FechaSalida date,
    SalarioBruto decimal(21,6) not null,
    SalarioHora decimal(21,6) not null,
    Planta nvarchar(50) not null,
    IdDepartamento int not null, --
    IdSupervisor int,
    IdTipoE int not null,

    PRIMARY KEY(IdEmpleado),
    CONSTRAINT FK_IdTipo_Empleados FOREIGN KEY (IdTipoE) REFERENCES TiposEmpleados(IdTipo),
    CONSTRAINT FK_IdDepartamento FOREIGN KEY (IdDepartamento) REFERENCES Departamento(IdDepartamento)
);

CREATE TABLE Marcas(
    Id int auto_increment,
    IdEmpleado int not null, --
    Entrada datetime not null,
    Salida datetime,

    PRIMARY KEY(id),
    CONSTRAINT fk_idEmpleado FOREIGN KEY (IdEmpleado) REFERENCES Empleados(IdEmpleado)
);

Create TABLE HorasExtra(
	Id int auto_increment,
    IdEmpleado int not null,
    Fecha date not null,
    CHExtras int not null,
    IdSupervisor int not null,
    Verificado varchar(1) , -- A(Aprobado), R(Rechazado) y P(Pendiente)
    
    Primary key(Id),
    CONSTRAINT fk_idEmpleado_HExtra FOREIGN KEY (IdEmpleado) REFERENCES Empleados(IdEmpleado),
	CONSTRAINT fk_idSupervisor_HExtra FOREIGN KEY (IdSupervisor) REFERENCES Empleados(IdEmpleado)
);

Create TABLE HorasLaboradas(
	Id int auto_increment,
    IdEmpleado int not null,
    Fecha date not null,
    CHoras int not null,
    
    Primary key(Id),
    CONSTRAINT fk_idEmpleado_HLaborada FOREIGN KEY (IdEmpleado) REFERENCES Empleados(IdEmpleado)
);

DELIMITER //
Create TRIGGER Insert_HorasExtra
AFTER INSERT
ON Marcas FOR EACH ROW
begin
	if(new.Salida IS not NULL)
		Then set @Salida = new.Salida, @IdEmpleado = new.IdEmpleado, @HSalida = (Select HoraSalida From Empleados INNER JOIN TiposEmpleados ON Empleados.IdTipoE = TiposEmpleados.IdTipo INNER JOIN Calendario ON Calendario.IdCalendario = TiposEmpleados.IdCalendario INNER JOIN DiasTrabajados ON DiasTrabajados.IdCalendario = Calendario.IdCalendario Where Empleados.IdEmpleado = @IdEmpleado Limit 1),
			@CantidadHorasLaborables = (ROUND(TIME_TO_SEC(timediff(Time(@Salida), @HSalida))/60/60));
		if(@CantidadHorasLaborables <> 0)
        Then set @IdSupervisor = (Select IdSupervisor From Empleados Where Empleados.IdEmpleado = @IdEmpleado);
		Insert into HorasExtra(IdEmpleado, Fecha, CHExtras, IdSupervisor, Verificado) values(@IdEmpleado, Date(@Salida), @CantidadHorasLaborables, @IdSupervisor, 'P');
        End if;
        if(Time(@Salida) >= @HSalida)
        Then set @Entrada = new.Entrada;
        set @HorasLaboradas =(ROUND(TIME_TO_SEC(timediff(@HSalida, Time(@Entrada)))/60/60));
        Insert into HorasLaboradas(IdEmpleado, Fecha, CHoras) values(@IdEmpleado, Date(@Salida), @HorasLaboradas);
        else  set @Entrada2 = new.Entrada;
        set @HorasLaboradas2 = (ROUND(TIME_TO_SEC(timediff(Time(@Salida), Time(@Entrada)))/60/60));
        Insert into HorasLaboradas(IdEmpleado, Fecha, CHoras) values(@IdEmpleado, Date(@Salida), @HorasLaboradas2);
        End if;
    End if;
    
END //
DELIMITER ;

DELIMITER //
Create TRIGGER Update_HorasExtra
AFTER UPDATE
ON Marcas FOR EACH ROW
begin
	set @Salida = new.Salida, @IdEmpleado = new.IdEmpleado, @HSalida = (Select HoraSalida From Empleados INNER JOIN TiposEmpleados ON Empleados.IdTipoE = TiposEmpleados.IdTipo INNER JOIN Calendario ON Calendario.IdCalendario = TiposEmpleados.IdCalendario INNER JOIN DiasTrabajados ON DiasTrabajados.IdCalendario = Calendario.IdCalendario Where Empleados.IdEmpleado = @IdEmpleado Limit 1),
		@CantidadHorasLaborables = (ROUND(TIME_TO_SEC(timediff(Time(@Salida), @HSalida))/60/60));
	if(@CantidadHorasLaborables <> 0)
		Then set @IdSupervisor = (Select IdSupervisor From Empleados Where Empleados.IdEmpleado = @IdEmpleado);
		Insert into HorasExtra(IdEmpleado, Fecha, CHExtras, IdSupervisor, Verificado) values(@IdEmpleado, Date(@Salida), @CantidadHorasLaborables, @IdSupervisor, 'P');
	End if;
	if(Time(@Salida) >= @HSalida)
		Then set @Entrada = new.Entrada;
		set @HorasLaboradas =(ROUND(TIME_TO_SEC(timediff(@HSalida, Time(@Entrada)))/60/60));
		Insert into HorasLaboradas(IdEmpleado, Fecha, CHoras) values(@IdEmpleado, Date(@Salida), @HorasLaboradas);
	else  set @Entrada2 = new.Entrada;
		set @HorasLaboradas2 = (ROUND(TIME_TO_SEC(timediff(Time(@Salida), Time(@Entrada)))/60/60));
		Insert into HorasLaboradas(IdEmpleado, Fecha, CHoras) values(@IdEmpleado, Date(@Salida), @HorasLaboradas2);
	End if;

END //
DELIMITER ;

DROP TRIGGER PlantaN.Insert_HorasExtra;

DELIMITER //
Create Procedure Marcar(IN IdEmpleado int, IN FechaDMarca nvarchar(30), INOUT MensajeSalida nvarchar(100))
BEGIN
	Set MensajeSalida = 'No se pudo realizar el proceso';
	if(IdEmpleado in (Select Marcas.IdEmpleado From Marcas Where Date(Marcas.Entrada) = Date(FechaDMarca)) and Date(FechaDMarca) in (Select Date(Marcas.Entrada) From Marcas Where Marcas.IdEmpleado = IdEmpleado) and (Select Salida From Marcas Where Date(Entrada) = Date(FechaDMarca) and Marcas.IdEmpleado = IdEmpleado) IS NOT NULL)
    Then Set MensajeSalida = 'NO SE PUEDE MARCAR 2 VECES EN EL MISMO DIA';
	ELSEif(IdEmpleado in (Select Marcas.IdEmpleado From Marcas Where Date(Marcas.Entrada) = Date(FechaDMarca)) and Date(FechaDMarca) in (Select Date(Marcas.Entrada) From Marcas Where Marcas.IdEmpleado = IdEmpleado))
	Then Update Marcas set Marcas.salida = FechaDMarca Where Marcas.IdEmpleado = IdEmpleado and Date(Marcas.Entrada) = Date(FechaDMarca);
    set MensajeSalida = 'El proceso se realizó de manera exitosa';
    else INSERT INTO Marcas(IdEmpleado, Entrada) VALUES (IdEmpleado, FechaDMarca);
    set MensajeSalida = 'El proceso se realizó de manera exitosa';
    End IF;
END //
DELIMITER ;


DELIMITER //
Create Procedure TraerIdEmpleado()
BEGIN
    Select IdEmpleado From Empleados;
END //
DELIMITER ;

-- INICIO DE PROGRAMACION DEL APARTADO DE EMPLEADO CRUD:-----------------------------------------------
-- Mostrar Datos de los Empleados:-------------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure MostrarEmpleados()
BEGIN
    Select IdEmpleado, Nombre, Apellidos, FechaIngreso, FechaSalida, SalarioBruto, SalarioHora, Planta, IdDepartamento, IdSupervisor, IdTipoE From Empleados;
END //
DELIMITER ;
-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Mostrar Todos Los Salarios Por Hora que Hay:-------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure MostrarSalariosHora()
BEGIN
    Select CostoHora From TiposEmpleados;
END //
DELIMITER ;
-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Mostrar Todos Los IdDepartamento que Hay:-------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure MostrarIdDepartamento()
BEGIN
    Select IdDepartamento From Departamento;
END //
DELIMITER ;

-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Mostrar Todos Los IdTipo que Hay:-------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure MostrarIdTipoEmpleado()
BEGIN
    Select IdTipo From tiposempleados;
END //
DELIMITER ;

-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Obtener El Salario Bruto segun el tipo de Empleado:-------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure ObtenerSalarioBruto( IN IdTipo int, INOUT SalarioBruto int)
BEGIN
	declare IdCalendarioN, CantidadDiasT, CantidadHT , SHora int;
    set IdCalendarioN:= (Select TiposEmpleados.IdCalendario From TiposEmpleados Where TiposEmpleados.IdTipo = IdTipo);
    set CantidadDiasT:= (Select Count(NumDia) From DiasTrabajados Where DiasTrabajados.IdCalendario = IdCalendarioN);
    set CantidadHT:= (Select CHorasLaborables From DiasTrabajados Where DiasTrabajados.IdCalendario = IdCalendarioN LIMIT 1);
    set SHora:= (Select TiposEmpleados.CostoHora From TiposEmpleados Where TiposEmpleados.IdTipo = IdTipo);
    set SalarioBruto = (SHora*CantidadHT*CantidadDiast*4);
END //
DELIMITER ;

-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Obtener El Salario Por Hora segun el tipo de Empleado:-------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure ObtenerSalarioHora( IN IdTipo int, INOUT SalarioHora int)
BEGIN
    set SalarioHora= (Select TiposEmpleados.CostoHora From TiposEmpleados Where TiposEmpleados.IdTipo = IdTipo);
END //
DELIMITER ;

-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Insertar un Empleado:-------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure InsertarEmpleado( IN NombreN nvarchar(50), IN ApellidosN nvarchar(100), IN FechaIngresoN nvarchar(30), 
	IN FechaSalidaN nvarchar(30), IN SalarioBrutoN int, IN SalarioHoraN int, IN PlantaN nvarchar(50), IN IdDepartamentoN int, IN IdSupervisorN int, 
    IN IdTipoEN int)
BEGIN
    INSERT INTO Empleados( Nombre, Apellidos, FechaIngreso, FechaSalida, SalarioBruto, SalarioHora, Planta, IdDepartamento, IdSupervisor, IdTipoE) 
	VALUES ( NombreN, ApellidosN, FechaIngresoN, FechaSalidaN, SalarioBrutoN, SalarioHoraN, PlantaN, IdDepartamentoN, IdSupervisorN, idTipoEN);
END //
DELIMITER ;

-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Editar un Empleado:-------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure EditarEmpleado( IN IdEmpleadoN int, IN NombreN nvarchar(50), IN ApellidosN nvarchar(100), IN FechaIngresoN nvarchar(30), 
	IN FechaSalidaN nvarchar(30), IN SalarioBrutoN int, IN SalarioHoraN int, IN IdDepartamentoN int, IN IdSupervisorN int, 
    IN IdTipoEN int)
BEGIN
    Update Empleados set Empleados.Nombre = NombreN, Empleados.Apellidos = ApellidosN, Empleados.FechaIngreso = FechaIngresoN, Empleados.FechaSalida = FechaSalidaN, Empleados.SalarioBruto = SalarioBrutoN, Empleados.SalarioHora = SalarioHoraN, Empleados.IdDepartamento = IdDepartamentoN, Empleados.IdSupervisor = IdSupervisorN, Empleados.IdTipoE = IdTipoEN Where Empleados.IdEmpleado = IdEmpleadoN;
END //
DELIMITER ;

-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Eliminar Marcas de un Empleado:-------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure EliminarMarcasDEmpleado( IN IdEmpleadoN int)
BEGIN
    Delete From Marcas Where Marcas.IdEmpleado = IdEmpleadoN;
END //
DELIMITER ;


-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Eliminar un Empleado:-------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure EliminarEmpleado( IN IdEmpleadoN int)
BEGIN
    Delete From Empleados Where Empleados.IdEmpleado = IdEmpleadoN;
END //
DELIMITER ;

-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Mostrar datos de TiposEmpleados:-------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure MostrarTiposEmpleados()
BEGIN
    Select TiposEmpleados.IdTipo, NombreTipo, IdCalendario, CostoHora, CostoHoraExtra, CostoHoraDoble from TiposEmpleados INNER JOIN NombreTipos oN TiposEmpleados.IdTipo = NombreTipos.IdTipo;
END //
DELIMITER ;

-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Devolver todos los IdCalendarios posibles:-------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure MostrarIdCalendarios()
BEGIN
    Select IdCalendario from Calendario;
END //
DELIMITER ;

-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Insertar un nuevo Nombre de Tipo de Empleado:-------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure InsertarNombreTipo(IN Nombre varchar(100), INOUT IdTipoFinal int)
BEGIN
    Insert into NombreTipos(NombreTipo) values(Nombre);
    
    set IdTipoFinal = (Select IdTipo From NombreTipos Where NombreTipo = Nombre);
END //
DELIMITER ;

-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Insertar un Tipo de Empleado:-------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure InsertarTipoEmpleado(IN IdTipoN int, IN IdCalendarioN int, IN CostoHoraN int, IN CostoHoraEN int, in CostoHoraDN int)
BEGIN
    Insert into TiposEmpleados(IdTipo ,  IdCalendario ,  CostoHora ,  CostoHoraExtra ,  CostoHoraDoble) values( IdTipoN ,  IdCalendarioN ,  CostoHoraN ,  CostoHoraEN ,  CostoHoraDN );
END //
DELIMITER ;

-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Editar un Tipo de Empleado:-------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure EditarTipoEmpleado(IN IdTipoN int, IN IdCalendarioN int, IN CostoHoraN int, IN CostoHoraEN int, in CostoHoraDN int, in NombreTipoN varchar(100))
BEGIN
	Update NombreTipos set NombreTipo = NombreTipoN Where IdTipo = IdTipoN;
    Update TiposEmpleados set IdCalendario = IdCalendarioN, CostoHora = CostoHoraN, CostoHoraExtra = CostoHoraEN, CostoHoraDoble = CostoHoraDN Where IdTipo = IdTipoN;
    Update Empleados set SalarioHora = CostoHoraN Where Empleados.IdTipoE = IdTipoN;
    Call ObtenerSalarioBruto(IdTipoN, @Salida);
    Update Empleados set SalarioBruto = @Salida where Empleados.IdTipoE = IdTipoN;
END //
DELIMITER ;

-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Obtener la cantidad de Empleados que usan un cierto TipoEmpleado:-------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure ObtenerCEmpleadosPT(IN IdTipoN int, INOUT  Cantidad int)
BEGIN
	set Cantidad = (SELECT COUNT(IdEmpleado) From Empleados Where IdTipoE = IdTipoN);
END //
DELIMITER ;

-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Eliminar TipoEmpleado:-------------------------------------------------------------------------------------------------------------
 DELIMITER //
Create Procedure EliminarTipoEmpleado(IN IdTipoN int)
BEGIN
	Delete From TiposEmpleados Where TiposEmpleados.IdTipo = IdTipoN;
    Delete From NombreTipos Where NombreTipos.IdTipo = IdTipoN; 
END //
DELIMITER ;

-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Mostrar informacion de departamentos:-------------------------------------------------------------------------------------------------------------
DELIMITER //
Create Procedure traernombredepartamento()
BEGIN
    Select IdDepartamento, Nombre From departamento;
END //
DELIMITER ;


-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Mostrar informacion de Tipos de Empleado:-------------------------------------------------------------------------------------------------------------
DELIMITER //
Create Procedure MostrarTiposEmpleado()
BEGIN
    Select TiposEmpleados.IdTipo, NombreTipos.NombreTipo, IdCalendario, CostoHora, CostoHoraExtra, CostoHoraDoble From TiposEmpleados INNER JOIN NombreTipos ON NombreTipos.IdTipo = TiposEmpleados.IdTipo;
END //
DELIMITER ;

-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Mostrar informacion de Tipos de Empleado:-------------------------------------------------------------------------------------------------------------
DELIMITER //
Create Procedure MostrarCalendarios()
BEGIN
    Select IdCalendario, NombreCalendario, TiposDPago.NombreTipo  From calendario INNER JOIN TiposDPago ON TiposDPago.IdTipo = Calendario.TipoDPago;
END //
DELIMITER ;

-- ---------------------------------------------------------------------------------------------------------------------------------------------------------
-- ---------------------------------------------------------------------------------------------------------------------------------------------------------
-- INICIO SP JERSON:----------------------------------------------------------------------------------------------------------------------------------------
-- JERSON CALENDARIO
DELIMITER //
Create Procedure consultarDiasTrabajadosPorCalendario(IN idCalendarioConsulta int)
BEGIN
    SELECT ds.NombreDia, dt.HoraEntrada, dt.HoraSalida, dt.CHorasLaborables
	FROM diastrabajados dt
		INNER JOIN diasdsemana ds ON ds.NumDia = dt.NumDia
		INNER JOIN calendario c ON c.IdCalendario = dt.IdCalendario
	WHERE c.IdCalendario = idCalendarioConsulta;
END //
DELIMITER ;

DELIMITER //
Create Procedure consultarDiasFeriadosPorCalendario(IN idCalendarioConsulta int)
BEGIN
    SELECT df.FechaDia, ma.NombreMes, df.TipoPago
    FROM diasferiados df
        INNER JOIN mesesdaneo ma ON ma.NumMes= df.NumMes
        INNER JOIN calendario c ON c.IdCalendario = df.IdCalendario
    WHERE c.IdCalendario = idCalendarioConsulta;
END //
DELIMITER ;

DELIMITER //
Create Procedure consultarTipoDPagoPorCalendario(IN idCalendarioConsulta int)
BEGIN
    SELECT tp.NombreTipo
	FROM calendario c
		INNER JOIN tiposdpago tp ON tp.IdTipo = c.TipoDPago
    WHERE IdCalendario = idCalendarioConsulta;
END //
DELIMITER ;

DELIMITER //
Create Procedure traernombrecalendario()
BEGIN
    Select IdCalendario, NombreCalendario From calendario;
END //
DELIMITER ;

DELIMITER //
Create Procedure anadirCalendario(IN nombreCalendario nvarchar(30), IN tipoDPago int, INOUT mensaje nvarchar(100))
BEGIN
	SET mensaje = 'No se ha podido añadir el calendario';
    
    -- cambiar id auto_increment
    
    INSERT INTO calendario( NombreCalendario, TipoDPago) VALUES (nombreCalendario, tipoDPago);
    SET mensaje = 'Calendario añadido con éxito';
END //
DELIMITER ;

DELIMITER //
Create Procedure modificarCalendario(IN idCalendarioModificar int, IN idTipoPago int, INOUT mensaje nvarchar(100))
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		SET mensaje = 'No se ha podido modificar el calendario';
    END;
    
    START TRANSACTION;
		UPDATE calendario SET TipoDPago = idTipoPago WHERE IdCalendario = idCalendarioModificar;
		SET mensaje = 'Calendario modificado con éxito';
    COMMIT;
END //
DELIMITER ;

DELIMITER //
Create Procedure eliminarCalendario(IN idCalendarioBorrar int, INOUT mensaje nvarchar(100))
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		SET mensaje = 'No se ha podido eliminar el calendario';
    END;
    
    START TRANSACTION;
		DELETE FROM calendario WHERE IdCalendario = idCalendarioBorrar;
		SET mensaje = 'Calendario eliminado con éxito';
    COMMIT;
END //
DELIMITER ;

DELIMITER //
Create Procedure anadirDepartamento(IN nombreDep nvarchar(30), INOUT mensaje nvarchar(100))
BEGIN
	SET mensaje = 'No se ha podido añadir el departamento';
    INSERT INTO departamento(Nombre) VALUES (nombreDep);
	SET mensaje = 'Departamento añadido con éxito';
END //
DELIMITER ;

DELIMITER //
Create Procedure modificarDepartamento(IN idDepModificar int, IN nuevoNombre nvarchar(100), INOUT mensaje nvarchar(100))
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		SET mensaje = 'No se ha podido modificar el departamento';
    END;
    
    START TRANSACTION;
		UPDATE departamento SET Nombre = nuevoNombre WHERE IdDepartamento = idDepModificar;
		SET mensaje = 'Departamento modificado con éxito';
    COMMIT;
END //
DELIMITER ;

DELIMITER //
Create Procedure eliminarDepartamento(IN idDepBorrar int, INOUT mensaje nvarchar(100))
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		SET mensaje = 'No se ha podido eliminar el departamento';
    END;
    
    START TRANSACTION;
		DELETE FROM departamento WHERE IdDepartamento = idDepBorrar;
		SET mensaje = 'Departamento eliminado con éxito';
    COMMIT;
END //
DELIMITER ;

-- JERSON DIAS FERIADOS
DELIMITER //
Create Procedure consultarDiasFeriadosPorCalendarioID(IN idCalendarioConsulta int)
BEGIN
    SELECT df.ID, df.FechaDia, ma.NombreMes, df.TipoPago
    FROM diasferiados df
        INNER JOIN mesesdaneo ma ON ma.NumMes= df.NumMes
        INNER JOIN calendario c ON c.IdCalendario = df.IdCalendario
    WHERE c.IdCalendario = idCalendarioConsulta;
END //
DELIMITER ;

DELIMITER //
Create Procedure anadirDiaFeriado(IN idCalendarioIns int, IN numDiaIns int, IN numMesIns int, IN tipoPaga char, INOUT mensaje nvarchar(100))
BEGIN
    SET mensaje = 'No se ha podido añadir el dia feriado';
    IF (idCalendarioIns IN (SELECT IdCalendario FROM diasferiados WHERE FechaDia = numDiaIns AND NumMes = numMesIns)) THEN
    SET mensaje = 'Dia feriado ya existe';
    ELSE

    -- cambiar id auto_increment

    INSERT INTO diasferiados(IdCalendario, FechaDia, NumMes, TipoPago) VALUES (idCalendarioIns, numDiaIns, numMesIns, tipoPaga);
    SET mensaje = 'Dia feriado añadido con éxito';
    END IF;
END //
DELIMITER ;

DELIMITER //
Create Procedure modificarDiaFeriado(IN idCalendarioIns int, IN idDiaCambiar int, IN numDiaIns int, IN numMesIns int, IN tipoPagaIns char, INOUT mensaje nvarchar(100))
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET mensaje = 'No se ha podido modificar el dia feriado';
    END;

    START TRANSACTION;
        IF (idCalendarioIns IN (SELECT IdCalendario FROM diasferiados WHERE FechaDia = numDiaIns AND NumMes = numMesIns)) THEN
        SET mensaje = 'Dia feriado ya existe';
        ELSE
        UPDATE diasferiados SET FechaDia = numDiaIns, NumMes = numMesIns, TipoPago = tipoPagaIns WHERE ID = idDiaCambiar;
        SET mensaje = 'Dia feriado modificado con éxito';
        END IF;
    COMMIT;
END //
DELIMITER ;

DELIMITER //
Create Procedure eliminarDiaFeriado(IN idDiaBorrar int, INOUT mensaje nvarchar(100))
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		SET mensaje = 'No se ha podido eliminar el dia feriado';
    END;
    
    START TRANSACTION;
		DELETE FROM diasferiados WHERE ID = idDiaBorrar;
		SET mensaje = 'Dia feriado eliminado con éxito';
    COMMIT;
END //
DELIMITER ;

-- NUEVO CODIGO MARCELO POST UNION:
-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Consulta3:-------------------------------------------------------------------------------------------------------------
DELIMITER //
Create Procedure Consulta3(IN FechaInicial nvarchar(30), IN FechaFinal nvarchar(30))
BEGIN
    Select IdEmpleado, Nombre From Empleados Where IdEmpleado not in (Select IdEmpleado From Marcas Where FechaInicial <= Marcas.Salida <= FechaFinal);
END //
DELIMITER ;


-- --------------------------------------------------------------------------------------------------------------------------------------------------------
-- Consulta5:-------------------------------------------------------------------------------------------------------------
DELIMITER //
Create Procedure Consulta5(IN IdEmpleadoN int)
BEGIN
    Select Empleados.IdEmpleado, Empleados.Nombre, Empleados.Apellidos, Empleados.FechaIngreso, Empleados.FechaSalida, Empleados.SalarioBruto, 
		Empleados.SalarioHora, Empleados.Planta, Departamento.Nombre, IdSupervisor, NombreTipos.NombreTipo From Empleados INNER JOIN Departamento 
		ON Departamento.IdDepartamento = Empleados.IdDepartamento INNER JOIN TiposEmpleados ON TiposEmpleados.IdTipo = Empleados.IdTipoE INNER JOIN 
        NombreTipos ON NombreTipos.IdTipo = TiposEmpleados.IdTipo Where Empleados.IdEmpleado = IdEmpleadoN;
END //
DELIMITER ;

DELIMITER //
Create Procedure Consulta7(IN IdSupervisorN int)
BEGIN
    Select Empleados.IdEmpleado, Empleados.Nombre, Empleados.Apellidos, Empleados.FechaIngreso, Empleados.FechaSalida, Empleados.SalarioBruto, 
		Empleados.SalarioHora, Empleados.Planta, Departamento.Nombre, IdSupervisor, NombreTipos.NombreTipo From Empleados INNER JOIN Departamento 
		ON Departamento.IdDepartamento = Empleados.IdDepartamento INNER JOIN TiposEmpleados ON TiposEmpleados.IdTipo = Empleados.IdTipoE INNER JOIN 
        NombreTipos ON NombreTipos.IdTipo = TiposEmpleados.IdTipo Where Empleados.IdSupervisor = IdSupervisorN;
END //
DELIMITER ;


DELIMITER //
Create Procedure TraerHEntrada(IN IdCalendarioN int)
BEGIN
    Select HoraEntrada From diastrabajados Where IdCalendario = IdCalendarioN Limit 1;
END //
DELIMITER ;


DELIMITER //
Create Procedure TraerHSalida(IN IdCalendarioN int)
BEGIN
    Select HoraSalida From diastrabajados Where IdCalendario = IdCalendarioN Limit 1;
END //
DELIMITER ;

DELIMITER //
Create Procedure IdCalendarioPIdEmpleado(IN IdEmpleadoN int, INOUT IdCalendarioN int)
BEGIN
    Set IdCalendarioN = (Select IdCalendario From Empleados INNER JOIN TiposEmpleados ON Empleados.IdTipoE = TiposEmpleados.IdTipo Where IdEmpleado = 1);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE verificarMarca(IN idEmpleadoM int, IN fechaMarca date)
BEGIN
    SELECT M.IdEmpleado, M.Entrada, M.Salida
    FROM Marcas M
    WHERE M.IdEmpleado = idEmpleadoM AND DATE(M.Entrada) = fechaMarca;
END //
DELIMITER ;


DELIMITER //
Create Procedure TraerIdEmpleadoPorIdCalendario(IN idCalendarioC int)
BEGIN
    Select IdEmpleado 
    From Empleados E
        INNER JOIN TiposEmpleados TE ON E.IdTipoE = TE.IdTipo
    WHERE TE.IdCalendario = idCalendarioC OR idCalendarioC = -1;
END //
DELIMITER ;

DELIMITER //
Create Procedure MarcarGenerador(IN IdEmpleadoM int, IN FechaDMarcaEntrada datetime, IN FechaDMarcaSalida datetime)
BEGIN
    INSERT INTO Marcas(IdEmpleado, Entrada, Salida) VALUES (IdEmpleadoM, FechaDMarcaEntrada, FechaDMarcaSalida);
END //
DELIMITER ;


-- ----------------------------------------------------------------------------------------------
-- DIAS TRABAJADOS CRUD

-- ----------------------------------------------------------------------------------------------
-- verificar que no exista dias trabajados con el idCalendario dado

DELIMITER //
CREATE PROCEDURE verificarNoExisteDiasTrabajadosPorCalendario(IN idCalendarioC int)
BEGIN
    SELECT DT.IdCalendario, DT.NumDia
    FROM DiasTrabajados DT
    WHERE DT.IdCalendario = idCalendarioC;
END //
DELIMITER ;


DELIMITER //
Create Procedure insertarDiasTrabajadosPorCalendario(IN idCalendarioD int, IN diaLunes int, IN diaMartes int, IN diaMiercoles int, IN diaJueves int, IN diaViernes int, IN diaSabado int, IN diaDomingo int, IN horaEntradaD time, IN horaSalidaD time)
BEGIN
	SET @CantidadHorasLaborables = (FLOOR(TIME_TO_SEC(timediff(horaSalidaD, horaEntradaD))/60/60));
	-- Lunes
    IF (diaLunes <> 0) THEN
		INSERT INTO DiasTrabajados(IdCalendario, NumDia, HoraEntrada, HoraSalida, CHorasLaborables) VALUES (idCalendarioD, diaLunes, horaEntradaD, horaSalidaD, @CantidadHorasLaborables);
    END IF;
    
    -- Martes
    IF (diaMartes <> 0) THEN
		INSERT INTO DiasTrabajados(IdCalendario, NumDia, HoraEntrada, HoraSalida, CHorasLaborables) VALUES (idCalendarioD, diaMartes, horaEntradaD, horaSalidaD, @CantidadHorasLaborables);
    END IF;
    
    -- Miercoles
    IF (diaMiercoles <> 0) THEN
		INSERT INTO DiasTrabajados(IdCalendario, NumDia, HoraEntrada, HoraSalida, CHorasLaborables) VALUES (idCalendarioD, diaMiercoles, horaEntradaD, horaSalidaD, @CantidadHorasLaborables);
    END IF;
    
    -- Jueves
    IF (diaJueves <> 0) THEN
		INSERT INTO DiasTrabajados(IdCalendario, NumDia, HoraEntrada, HoraSalida, CHorasLaborables) VALUES (idCalendarioD, diaJueves, horaEntradaD, horaSalidaD, @CantidadHorasLaborables);
    END IF;
    
    -- Viernes
    IF (diaViernes <> 0) THEN
		INSERT INTO DiasTrabajados(IdCalendario, NumDia, HoraEntrada, HoraSalida, CHorasLaborables) VALUES (idCalendarioD, diaViernes, horaEntradaD, horaSalidaD, @CantidadHorasLaborables);
    END IF;
    
    -- Sabado
    IF (diaSabado <> 0) THEN
		INSERT INTO DiasTrabajados(IdCalendario, NumDia, HoraEntrada, HoraSalida, CHorasLaborables) VALUES (idCalendarioD, diaSabado, horaEntradaD, horaSalidaD, @CantidadHorasLaborables);
    END IF;
    
    -- Domingo
    IF (diaDomingo <> 0) THEN
		INSERT INTO DiasTrabajados(IdCalendario, NumDia, HoraEntrada, HoraSalida, CHorasLaborables) VALUES (idCalendarioD, diaDomingo, horaEntradaD, horaSalidaD, @CantidadHorasLaborables);
    END IF;
END //
DELIMITER ;

-- ----------------------------------------------------------------------------------------------
-- modificar dias trabajados por idCalendario

DELIMITER //
CREATE PROCEDURE modificarHoraDiasTrabajadosPorCalendario(IN idCalendarioM int, IN horaEntradaM time, IN horaSalidaM time)
BEGIN
    SET @CantidadHorasLaborables = (FLOOR(TIME_TO_SEC(timediff(horaSalidaM, horaEntradaM))/60/60));
    UPDATE DiasTrabajados SET HoraEntrada = horaEntradaM, HoraSalida = horaSalidaM, CHorasLaborables = @CantidadHorasLaborables WHERE IdCalendario = idCalendarioM;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE eliminarHoraDiasTrabajadosPorCalendario(IN idCalendarioM int)
BEGIN
    DELETE FROM DiasTrabajados WHERE IdCalendario = idCalendarioM;
END //
DELIMITER ;


Call Consulta3('2023-11-26 02:07:42', '2023-11-26 10:45:00');

Call Consulta5(32);

Call Consulta7(10);

Call TraerHEntrada(1);

Call TraerHSalida(1);

Call IdCalendarioPIdEmpleado(1, @Salida);
select @Salida;

Call MarcarGenerador(1, '2023-11-27 08:00:00', '2023-11-27 18:00:00');

Insert Into Marcas(IdEmpleado, Entrada) values(2, '2023-11-27 06:00:00');

Update Marcas set Marcas.Salida =  '2023-11-27 17:00:00' Where Marcas.IdEmpleado = 2 and Date(Marcas.Entrada) = '2023-11-27';

