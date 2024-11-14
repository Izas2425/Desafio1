-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 11-11-2024 a las 22:07:22
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `desafio1`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bombarderos`
--

CREATE TABLE `bombarderos` (
  `idmisiones` int(11) NOT NULL,
  `objetivos` int(11) NOT NULL,
  `carga` tinyint(1) NOT NULL,
  `pasajeros` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `combates`
--

CREATE TABLE `combates` (
  `idmisiones` int(11) NOT NULL,
  `cazas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `misiones`
--

CREATE TABLE `misiones` (
  `idmisiones` int(11) NOT NULL,
  `nombre` int(11) NOT NULL,
  `experiencia` int(11) NOT NULL,
  `matriculanave` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `misionesasignadas`
--

CREATE TABLE `misionesasignadas` (
  `id` int(11) NOT NULL,
  `idusuario` int(11) NOT NULL,
  `idmision` int(11) NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `naves`
--

CREATE TABLE `naves` (
  `matricula` varchar(15) NOT NULL,
  `foto` varchar(200) NOT NULL,
  `tipo` varchar(15) NOT NULL,
  `carga` tinyint(1) NOT NULL,
  `pasajeros` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `naves`
--

INSERT INTO `naves` (`matricula`, `foto`, `tipo`, `carga`, `pasajeros`) VALUES
('hbt21', '', 'combate', 0, 0),
('jkh72', '', 'vuelo', 1, 1),
('mdt23', '', 'bombardero', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(15) NOT NULL,
  `password` varchar(20) NOT NULL,
  `role` varchar(15) NOT NULL,
  `edad` int(11) NOT NULL,
  `experiencia` int(11) NOT NULL,
  `foto` varchar(200) NOT NULL,
  `activado` int(11) NOT NULL,
  `nivel` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `password`, `role`, `edad`, `experiencia`, `foto`, `activado`, `nivel`) VALUES
(1, 'Dark Vader', 'Vader', 'Vader', 0, 0, '', 0, ''),
(2, 'Estefania', 'domingo', 'Piloto', 28, 90, '', 0, 'Intermedio'),
(3, 'Mateo', 'miercoles', 'Piloto', 28, 95, '', 0, 'Intermedio'),
(5, 'Lucia', 'domingo', 'Piloto', 25, 45, '', 0, 'Novato'),
(6, 'David', 'domingo', 'Piloto', 26, 65, '', 0, 'Intermedio');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vuelos`
--

CREATE TABLE `vuelos` (
  `idmisiones` int(11) NOT NULL,
  `duracion` int(11) NOT NULL,
  `carga` tinyint(1) NOT NULL,
  `pasajeros` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `bombarderos`
--
ALTER TABLE `bombarderos`
  ADD PRIMARY KEY (`idmisiones`);

--
-- Indices de la tabla `combates`
--
ALTER TABLE `combates`
  ADD PRIMARY KEY (`idmisiones`);

--
-- Indices de la tabla `misiones`
--
ALTER TABLE `misiones`
  ADD PRIMARY KEY (`idmisiones`),
  ADD UNIQUE KEY `matriculanave` (`matriculanave`);

--
-- Indices de la tabla `misionesasignadas`
--
ALTER TABLE `misionesasignadas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idusuario` (`idusuario`,`idmision`),
  ADD KEY `idmision` (`idmision`);

--
-- Indices de la tabla `naves`
--
ALTER TABLE `naves`
  ADD PRIMARY KEY (`matricula`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `vuelos`
--
ALTER TABLE `vuelos`
  ADD PRIMARY KEY (`idmisiones`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `misiones`
--
ALTER TABLE `misiones`
  MODIFY `idmisiones` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `misionesasignadas`
--
ALTER TABLE `misionesasignadas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `bombarderos`
--
ALTER TABLE `bombarderos`
  ADD CONSTRAINT `bombarderos_ibfk_1` FOREIGN KEY (`idmisiones`) REFERENCES `misiones` (`idmisiones`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `combates`
--
ALTER TABLE `combates`
  ADD CONSTRAINT `combates_ibfk_1` FOREIGN KEY (`idmisiones`) REFERENCES `misiones` (`idmisiones`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `misiones`
--
ALTER TABLE `misiones`
  ADD CONSTRAINT `misiones_ibfk_1` FOREIGN KEY (`matriculanave`) REFERENCES `naves` (`matricula`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `misionesasignadas`
--
ALTER TABLE `misionesasignadas`
  ADD CONSTRAINT `misionesasignadas_ibfk_1` FOREIGN KEY (`idmision`) REFERENCES `misiones` (`idmisiones`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `misionesasignadas_ibfk_2` FOREIGN KEY (`idusuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `vuelos`
--
ALTER TABLE `vuelos`
  ADD CONSTRAINT `vuelos_ibfk_1` FOREIGN KEY (`idmisiones`) REFERENCES `misiones` (`idmisiones`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
