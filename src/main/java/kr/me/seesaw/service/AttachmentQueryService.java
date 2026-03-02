package kr.me.seesaw.service;

import kr.me.seesaw.model.AttachmentModel;

import java.util.List;

public interface AttachmentQueryService {

    List<AttachmentModel> getAttachments(String referenceId);

}
