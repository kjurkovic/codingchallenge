package com.kjurkovic.networking.core.handlers

import retrofit2.HttpException
import retrofit2.Response

class ApiException(
    response: Response<*>,
) : HttpException(response)
