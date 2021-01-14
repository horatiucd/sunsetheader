package com.hcd.sunsetheader.review;

import java.time.LocalDateTime;
import java.util.Optional;

public interface Sunsetable {
	
	Optional<LocalDateTime> sunsetDate();
}
