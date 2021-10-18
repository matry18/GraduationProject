
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
