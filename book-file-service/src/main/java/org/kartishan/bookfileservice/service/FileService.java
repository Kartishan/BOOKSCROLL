package org.kartishan.bookfileservice.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final GridFsTemplate gridFsTemplate;

    public Boolean storeFile(MultipartFile file, UUID bookId) throws IOException {
        try {
            DBObject metaData = new BasicDBObject();
            metaData.put("bookId", bookId.toString());
            ObjectId fileId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metaData);
            return true;
        }catch (Exception e){
            return false;
        }

    }
    public GridFSFile retrieveFileByBookId(UUID bookId) {
        return gridFsTemplate.findOne(new Query(Criteria.where("metadata.bookId").is(bookId.toString())));
    }

    public InputStreamResource downloadFileAsResource(UUID bookId) {
        GridFSFile gridFSFile = retrieveFileByBookId(bookId);
        if (gridFSFile != null) {
            GridFsResource gridFsResource = gridFsTemplate.getResource(gridFSFile);
            try {
                return new InputStreamResource(gridFsResource.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException("Error while retrieving file with ID: " + bookId, e);
            }
        } else {
            throw new RuntimeException("File with ID: " + bookId + " not found");
        }
    }
}
