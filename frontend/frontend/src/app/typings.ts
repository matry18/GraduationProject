

export interface SagaResponseDto {
  sagaId: string;
  serviceName: string;
  sagaStatus: SagaStatus;
  errorMessage: string;
  receivingTime: Date;
}

export enum SagaStatus {
  SUCCESS = "SUCCESS",
  FAILED = "FAILED",
}

export interface ResidentDto {
  id: string,
  firstname: string,
  lastname: string,
  email: string,
  phoneNumber: string,
  department: DepartmentDto,
  //these should be removed when we get Kafka, Orchestrator, and Authentication services up.
  username: string,
  password: string
}

export interface SagaResidentDto {
  sagaId: String;
  residentDto: ResidentDto;
}

export interface SagaEmployeeDto {
  sagaId: string;
  employeeDto: EmployeeDto;
}

export interface EmployeeDto {
  id: string,
  firstname: string,
  lastname: string,
  email: string,
  phoneNumber: string,
  department: DepartmentDto,
  //these should be removed when we get Kafka, Orchestrator, and Authentication services up.
  username: string,
  password: string
}

export interface DepartmentDto {
  id: string,
  departmentName: string
}

export interface LoginDto {
  employeeId: string;
  username: string;
  password: string;
}



export interface LoginState {
  logged_in: boolean;
}

export interface CurrentUserState {
  currentUser: EmployeeDto;
}
