package cn.wstom.monitor.entity;

import io.openvidu.java.client.OpenViduRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamUser {
		private String name;
	    private String pass;
	    private OpenViduRole role;
}
