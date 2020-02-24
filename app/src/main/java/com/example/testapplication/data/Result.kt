package com.example.testapplication.data

sealed class Result<T>

class Loading<T>: Result<T>()
class Success<T> constructor(val data: T): Result<T>()
class Failure<T> constructor(val error: Error): Result<T>()