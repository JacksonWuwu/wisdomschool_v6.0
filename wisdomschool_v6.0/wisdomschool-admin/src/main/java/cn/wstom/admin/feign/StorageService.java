package cn.wstom.admin.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "wisdomschool-storage-service")
public interface StorageService {
}
