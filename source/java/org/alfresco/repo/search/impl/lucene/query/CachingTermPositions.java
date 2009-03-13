/*
 * Copyright (C) 2005-2007 Alfresco Software Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

 * As a special exception to the terms and conditions of version 2.0 of 
 * the GPL, you may redistribute this Program in connection with Free/Libre 
 * and Open Source Software ("FLOSS") applications as described in Alfresco's 
 * FLOSS exception.  You should have recieved a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 * http://www.alfresco.com/legal/licensing"
 */
package org.alfresco.repo.search.impl.lucene.query;

import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.index.TermPositions;

/**
 * @author andyh
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CachingTermPositions implements TermPositions
{
    int[] results;

    int position = -1;

    int last = -1;

    TermPositions delegate;

    CachingTermPositions(TermPositions delegate)
    {
        this.delegate = delegate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.lucene.index.TermPositions#nextPosition()
     */
    public int nextPosition() throws IOException
    {
        if (results == null)
        {
            results = new int[freq()];
        }
        position++;
        if (last < position)
        {
            results[position] = delegate.nextPosition();
            last = position;
        }
        return results[position];

    }

    public void reset()
    {
        position = -1;
    }

    private void clear()
    {
        position = -1;
        last = -1;
        results = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.lucene.index.TermDocs#seek(org.apache.lucene.index.Term)
     */
    public void seek(Term term) throws IOException
    {
        delegate.seek(term);
        clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.lucene.index.TermDocs#seek(org.apache.lucene.index.TermEnum)
     */
    public void seek(TermEnum termEnum) throws IOException
    {
        delegate.seek(termEnum);
        clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.lucene.index.TermDocs#doc()
     */
    public int doc()
    {
        return delegate.doc();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.lucene.index.TermDocs#freq()
     */
    public int freq()
    {
        return delegate.freq();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.lucene.index.TermDocs#next()
     */
    public boolean next() throws IOException
    {
        if (delegate.next())
        {
            clear();
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.lucene.index.TermDocs#read(int[], int[])
     */
    public int read(int[] docs, int[] freqs) throws IOException
    {
        int answer = delegate.read(docs, freqs);
        clear();
        return answer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.lucene.index.TermDocs#skipTo(int)
     */
    public boolean skipTo(int target) throws IOException
    {
        if (delegate.skipTo(target))
        {
            clear();
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.lucene.index.TermDocs#close()
     */
    public void close() throws IOException
    {
        delegate.close();
        clear();
    }
    
    public byte[] getPayload(byte[] data, int offset) throws IOException
    {
        return delegate.getPayload(data, offset);
    }

    public int getPayloadLength()
    {
        return delegate.getPayloadLength();
    }

    public boolean isPayloadAvailable()
    {
        return delegate.isPayloadAvailable();
    }

}