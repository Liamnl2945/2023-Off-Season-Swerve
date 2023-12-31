        // Copyright (c) FIRST and other WPILib contributors.
        // Open Source Software; you can modify and/or share it under the terms of
        // the WPILib BSD license file in the root directory of this project.

        package frc.robot;

        

        import com.ctre.phoenix.motorcontrol.NeutralMode;
        import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
        import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
        import com.ctre.phoenix.motorcontrol.can.TalonFX;
        import com.ctre.phoenix.sensors.AbsoluteSensorRange;
        import com.ctre.phoenix.sensors.CANCoderConfiguration;
        import com.ctre.phoenix.sensors.SensorInitializationStrategy;
        import com.ctre.phoenix.sensors.SensorTimeBase;
        import com.ctre.phoenixpro.signals.NeutralModeValue;
        import frc.robot.subsystems.Drive.KinematicLimits;
        import frc.robot.subsystems.ServoMotorSubsystem.ServoMotorSubsystemConstants;
        import frc.robot.subsystems.ServoMotorSubsystem.TalonFXConstants;
        import lib.swerve.SwerveDriveKinematics;
        import lib.swerve.SwerveModule.SwerveModuleConstants;
        import lib.Conversions;
        import edu.wpi.first.math.util.Units;
        import edu.wpi.first.math.geometry.Pose3d;
        import edu.wpi.first.math.geometry.Rotation3d;
        import edu.wpi.first.math.geometry.Transform3d;
        import edu.wpi.first.math.trajectory.TrajectoryConfig;
        import edu.wpi.first.math.trajectory.TrapezoidProfile;
        import edu.wpi.first.math.trajectory.constraint.CentripetalAccelerationConstraint;
        /**
         * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
         * constants. This class should not be used for any other purpose. All constants should be declared
         * globally (i.e. public static). Do not put anything functional in this class.
         *
         * <p>It is advised to statically import this class (or one of its inner classes) wherever the
         * constants are needed, to reduce verbosity.
         */
        public class Constants {
        
                // toggle constants between comp bot and practice bot ("beta")
                public static boolean isComp;
                public static boolean isBeta;
                public static boolean isCompBot() {
                        return isComp;
                }
                public static boolean isBetaBot() {
                        return isBeta;
                }
        
                // Disables extra smart dashboard outputs that slow down the robot
                public static final boolean disableExtraTelemetry = false;
        
                // robot loop time
                public static final double kLooperDt = 0.02;
        
                /* Control Board */
                public static final double kJoystickThreshold = 0.2;
                public static final int kButtonGamepadPort = 1;
        
                public static final double stickDeadband = 0.15;
        
                // Timeout constants
                public static final int kLongCANTimeoutMs = 100;
                public static final int kCANTimeoutMs = 10;
        
                public static final class SwerveConstants {
                        public static final boolean invertGyro = false; // Always ensure Gyro is CCW+ CW-
                
                        /* Drivetrain Constants */
                        public static final double trackWidth = Units.inchesToMeters(20.75);
                        public static final double wheelBase = Units.inchesToMeters(20.75);
                
                        public static final double wheelDiameter = Units.inchesToMeters(3.85);
                        public static final double wheelCircumference = wheelDiameter * Math.PI;
                
                        public static final double openLoopRamp = 0.25;
                        public static final double closedLoopRamp = 0.0;
                
                        public static final double driveGearRatio = 6.12;
                        public static final double angleGearRatio = (150.0/7.0);
                
                        public static final edu.wpi.first.math.geometry.Translation2d[] swerveModuleLocations = {
                                new edu.wpi.first.math.geometry.Translation2d(wheelBase / 2.0, trackWidth / 2.0),
                                new edu.wpi.first.math.geometry.Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
                                new edu.wpi.first.math.geometry.Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
                                new edu.wpi.first.math.geometry.Translation2d(-wheelBase / 2.0, -trackWidth / 2.0)
                        };
        public static final SwerveDriveKinematics kKinematics = new SwerveDriveKinematics(swerveModuleLocations);

         /* Swerve Current Limiting */
        public static final int angleContinuousCurrentLimit = 25;
        public static final int anglePeakCurrentLimit = 40;
         public static final double anglePeakCurrentDuration = 0.1;
         public static final boolean angleEnableCurrentLimit = true;
 
         public static final int driveContinuousCurrentLimit = 50;
         public static final int drivePeakCurrentLimit = 60;
         public static final double drivePeakCurrentDuration = 0.1;
         public static final boolean driveEnableCurrentLimit = true;
 
         /* Swerve Profiling Values */
         public static final double maxSpeed = 5.4864; // meters per second
         public static final double maxAngularVelocity = 11.5;

         public static final double maxAttainableSpeed = maxSpeed * 0.85; // Max out at 85% to make sure speeds are attainable (4.6 mps)

        /* Angle Motor PID Values */
        public static final double angleKP = 0.3;
        public static final double angleKI = 0.0;
        public static final double angleKD = 0.0;
        public static final double angleKF = 0.0;

        /* Drive Motor PID Values */
        public static final double driveKP = 4.0;
        public static final double driveKI = 0.0;
        public static final double driveKD = 0.0;
        public static final double driveKF = 12.0 / Conversions.MPSToRPS(maxSpeed, wheelCircumference, driveGearRatio);

        /* Neutral Modes */
        public static final NeutralMode angleNeutralMode = NeutralMode.Coast;
        public static final NeutralMode driveNeutralMode = NeutralMode.Brake;

        /* Motor Inverts */
        public static final boolean driveMotorInvert = false;
        public static final boolean angleMotorInvert = true;

        /* Angle Encoder Invert */
        public static final boolean canCoderInvert = false;

        /* Controller Invert */
        public static final boolean invertYAxis = false;
        public static final boolean invertRAxis = false;
        public static final boolean invertXAxis = false;

        public static final KinematicLimits kUncappedLimits = new KinematicLimits();
        static {
                kUncappedLimits.kMaxDriveVelocity = maxSpeed;
                kUncappedLimits.kMaxAccel = Double.MAX_VALUE;
                kUncappedLimits.kMaxAngularVelocity = maxAngularVelocity;
                kUncappedLimits.kMaxAngularAccel = Double.MAX_VALUE;
        }

        public static final KinematicLimits kScoringLimits = new KinematicLimits();
        static {
                kScoringLimits.kMaxDriveVelocity = 2.0;
                kScoringLimits.kMaxAccel = Double.MAX_VALUE;
                kScoringLimits.kMaxAngularVelocity = Math.PI;  // Rad/Sec
                kScoringLimits.kMaxAngularAccel = 10 * Math.PI; //2 * Math.PI;
        }

        public static final KinematicLimits kLoadingStationLimits = new KinematicLimits();
        static {
                kLoadingStationLimits.kMaxDriveVelocity = 1.5;
                kLoadingStationLimits.kMaxAccel = Double.MAX_VALUE;
                kLoadingStationLimits.kMaxAngularVelocity = maxAngularVelocity;
                kLoadingStationLimits.kMaxAngularAccel = Double.MAX_VALUE;
        }

        public static final KinematicLimits kAutoLimits = new KinematicLimits();
        static {
                kAutoLimits.kMaxDriveVelocity = maxAttainableSpeed;
                kAutoLimits.kMaxAccel = Double.MAX_VALUE;
                kAutoLimits.kMaxAngularVelocity =  Double.MAX_VALUE; // Rad/Sec
                kAutoLimits.kMaxAngularAccel = Double.MAX_VALUE; // 2 * Math.PI;
        }

        /*** MODULE SPECIFIC CONSTANTS ***/
        /* Front Left Module - Module 0 */
        public static final class Mod0 {
                public static final double betaAngleOffset = 347.16;
                public static final double compAngleOffset = 323.34;

                public static SwerveModuleConstants SwerveModuleConstants() {
                        return new SwerveModuleConstants(Ports.FL_DRIVE, Ports.FL_ROTATION, Ports.FL_CANCODER,
                                        isComp ? compAngleOffset : betaAngleOffset);
                }
        }

        /* Front Right Module - Module 1 */
        public static final class Mod1 {
                public static final double betaAngleOffset = 338.99;
                public static final double compAngleOffset = 325.1;

                public static SwerveModuleConstants SwerveModuleConstants() {
                        return new SwerveModuleConstants(Ports.FR_DRIVE, Ports.FR_ROTATION, Ports.FR_CANCODER,
                                        isComp ? compAngleOffset : betaAngleOffset);
                }
        }

        /* Back Left Module - Module 2 */
        public static final class Mod2 {
                public static final double betaAngleOffset = 331.52;
                public static final double compAngleOffset = 344.7;

                public static SwerveModuleConstants SwerveModuleConstants() {
                        return new SwerveModuleConstants(Ports.BL_DRIVE, Ports.BL_ROTATION, Ports.BL_CANCODER,
                                        isComp ? compAngleOffset : betaAngleOffset);
                }
        }

        /* Back Right Module - Module 3 */
        public static final class Mod3 {
                public static final double betaAngleOffset = 309.81;
                public static final double compAngleOffset = 245.74;

                
                public static SwerveModuleConstants SwerveModuleConstants() {
                        return new SwerveModuleConstants(Ports.BR_DRIVE, Ports.BR_ROTATION, Ports.BR_CANCODER,
                                        isComp ? compAngleOffset : betaAngleOffset);
                }
        }

        public static com.ctre.phoenixpro.configs.TalonFXConfiguration swerveDriveProFXConfig() {
                com.ctre.phoenixpro.configs.TalonFXConfiguration config = new com.ctre.phoenixpro.configs.TalonFXConfiguration();


                config.CurrentLimits.SupplyCurrentLimitEnable = Constants.SwerveConstants.driveEnableCurrentLimit;
                config.CurrentLimits.SupplyCurrentLimit = Constants.SwerveConstants.driveContinuousCurrentLimit;
                

                config.Voltage.PeakForwardVoltage = 12.0;
                config.Voltage.PeakReverseVoltage = -12.0;

                config.Slot0.kP = Constants.SwerveConstants.driveKP;
                config.Slot0.kI = Constants.SwerveConstants.driveKI;
                config.Slot0.kD = Constants.SwerveConstants.driveKD;
                config.Slot0.kV = Constants.SwerveConstants.driveKF;

                config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

                config.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = Constants.SwerveConstants.openLoopRamp;
                config.OpenLoopRamps.VoltageOpenLoopRampPeriod = Constants.SwerveConstants.openLoopRamp;
                return config;
        }

        public static TalonFXConfiguration swerveDriveFXConfig() {
                TalonFXConfiguration config = new TalonFXConfiguration();
                SupplyCurrentLimitConfiguration driveSupplyLimit = new SupplyCurrentLimitConfiguration(
                        Constants.SwerveConstants.driveEnableCurrentLimit,
                        Constants.SwerveConstants.driveContinuousCurrentLimit,
                        Constants.SwerveConstants.drivePeakCurrentLimit,
                        Constants.SwerveConstants.drivePeakCurrentDuration);

                config.slot0.kP = Constants.SwerveConstants.driveKP;
                config.slot0.kI = Constants.SwerveConstants.driveKI;
                config.slot0.kD = Constants.SwerveConstants.driveKD;
                config.slot0.kF = Constants.SwerveConstants.driveKF;
                config.supplyCurrLimit = driveSupplyLimit;
                config.initializationStrategy = SensorInitializationStrategy.BootToZero;
                config.openloopRamp = Constants.SwerveConstants.openLoopRamp;
                config.closedloopRamp = Constants.SwerveConstants.closedLoopRamp;
                return config;
        }

        public static TalonFXConfiguration swerveAngleFXConfig() {
                TalonFXConfiguration angleConfig = new TalonFXConfiguration();
                SupplyCurrentLimitConfiguration angleSupplyLimit = new SupplyCurrentLimitConfiguration(
                        Constants.SwerveConstants.angleEnableCurrentLimit,
                        Constants.SwerveConstants.angleContinuousCurrentLimit,
                        Constants.SwerveConstants.anglePeakCurrentLimit,
                        Constants.SwerveConstants.anglePeakCurrentDuration);

                angleConfig.slot0.kP = Constants.SwerveConstants.angleKP;
                angleConfig.slot0.kI = Constants.SwerveConstants.angleKI;
                angleConfig.slot0.kD = Constants.SwerveConstants.angleKD;
                angleConfig.slot0.kF = Constants.SwerveConstants.angleKF;
                angleConfig.supplyCurrLimit = angleSupplyLimit;
                angleConfig.initializationStrategy = SensorInitializationStrategy.BootToZero;
                return angleConfig;
                }

                public static CANCoderConfiguration swerveCancoderConfig() {
                        CANCoderConfiguration config = new CANCoderConfiguration();
                        config.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
                        config.sensorDirection = Constants.SwerveConstants.canCoderInvert;
                        config.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
                        config.sensorTimeBase = SensorTimeBase.PerSecond;
                        return config;
                }
        }

        public static final class SnapConstants {
                public static final double kP = 6.0;
                public static final double kI = 0.5;
                public static final double kD = 0.2;
                public static final double snapTimeout = 0.25;
                public static final double snapEpsilon = 1.0;
        }


        public static final class AutoConstants {

                public static final double kPXController = 6.7;
                public static final double kPYController = 6.7;
        
                public static final double kDXController = 0.0;
                public static final double kDYController = 0.0;
        
                public static final double kPThetaController = 2.0;

                // Constraint for the motion profilied robot angle controller (Radians)
                public static final double kMaxAngularSpeed = 2.0 * Math.PI; 
                public static final double kMaxAngularAccel = 2.0 * Math.PI * kMaxAngularSpeed;

                public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
                                kMaxAngularSpeed, kMaxAngularAccel);

                // Static factory for creating trajectory configs
                public static TrajectoryConfig createConfig(double maxSpeed, double maxAccel, double startSpeed, double endSpeed) {
                        TrajectoryConfig config = new TrajectoryConfig(maxSpeed, maxAccel);
                        config.setStartVelocity(startSpeed);
                        config.setEndVelocity(endSpeed);
                        config.addConstraint(new CentripetalAccelerationConstraint(10.0));
                        return config;
                }
        }






        public static class OperatorConstants {
        public static final int kDriverControllerPort = 0;
        }
}
