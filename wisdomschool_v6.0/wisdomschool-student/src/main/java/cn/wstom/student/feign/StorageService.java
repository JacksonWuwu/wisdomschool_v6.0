package cn.wstom.student.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "wisdomschool-storage-service")
public interface StorageService {
}
