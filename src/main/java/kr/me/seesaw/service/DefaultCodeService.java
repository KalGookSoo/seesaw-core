package kr.me.seesaw.service;

import kr.me.seesaw.model.CodeModel;
import kr.me.seesaw.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class DefaultCodeService implements CodeService {

    private final CodeRepository codeRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CodeModel> getAllCodes() {
        return codeRepository.findAll(Sort.by(Sort.Direction.ASC, "sequence"))
                .stream()
                .map(CodeModel::new)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CodeModel> getAllCodesByName(String name) {
        return codeRepository.findByName(name)
                .stream()
                .map(CodeModel::new)
                .toList();
    }

}
