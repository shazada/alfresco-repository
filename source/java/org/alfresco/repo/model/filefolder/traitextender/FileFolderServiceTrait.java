
package org.alfresco.repo.model.filefolder.traitextender;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.query.PagingRequest;
import org.alfresco.query.PagingResults;
import org.alfresco.service.cmr.model.FileExistsException;
import org.alfresco.service.cmr.model.FileFolderServiceType;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.traitextender.Trait;
import org.alfresco.util.Pair;

public interface FileFolderServiceTrait extends Trait
{
    FileInfo createFileInfo(NodeRef nodeRef, QName typeQName, boolean isFolder, boolean isHidden,
                Map<QName, Serializable> properties);

    FileFolderServiceType getType(QName typeQName);

    List<FileInfo> list(NodeRef contextNodeRef);

    PagingResults<FileInfo> list(final NodeRef contextNodeRef, boolean files, boolean folders, String pattern,
                Set<QName> ignoreQNames, List<Pair<QName, Boolean>> sortProps, PagingRequest pagingRequest);

    PagingResults<FileInfo> list(NodeRef contextNodeRef, boolean files, boolean folders, Set<QName> ignoreQNames,
                List<Pair<QName, Boolean>> sortProps, PagingRequest pagingRequest);

    PagingResults<FileInfo> list(NodeRef rootNodeRef, Set<QName> searchTypeQNames, Set<QName> ignoreAspectQNames,
                List<Pair<QName, Boolean>> sortProps, PagingRequest pagingRequest);

    List<FileInfo> search(NodeRef contextNodeRef, String namePattern, boolean fileSearch, boolean folderSearch,
                boolean includeSubFolders);

    Pair<Set<QName>, Set<QName>> buildSearchTypesAndIgnoreAspects(boolean files, boolean folders,
                Set<QName> ignoreQNameTypes);

    FileInfo rename(NodeRef sourceNodeRef, String newName) throws FileExistsException, FileNotFoundException;
}
