package kr.me.seesaw.service;

import kr.me.seesaw.command.CreateReplyCommand;
import kr.me.seesaw.command.UpdateReplyCommand;
import kr.me.seesaw.model.ReplyModel;

public interface ReplyService {

    ReplyModel find(String id);

    ReplyModel create(CreateReplyCommand command);

    ReplyModel update(String id, UpdateReplyCommand command);

    void delete(String id);

}
