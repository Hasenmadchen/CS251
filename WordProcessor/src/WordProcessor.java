import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Simple Word Processing class
 *
 * <p>Purdue University -- CS25100 -- Fall 2019 -- Tries</p>
 */
public class WordProcessor
{
  private Node wordTrie;  //Root Node of the Trie

  /**
   * A simple Node class representing each
   * individual node of the trie
   */
  public class Node
  {
    protected char c;
    protected Node left, equal, right;
    protected boolean isEnd = false;

    /**
     * Constructor for Node class
     *
     * @param ca Character assigned to the node
     */
    public Node( char ca )
    {
      c = ca;
    }
  }

  /**
   * Defualt constructor for the WordProcessor class
   */
  public WordProcessor()
  {
    wordTrie = null;
  }

  /**
   * Method to add a string to the trie
   *
   * @param s String to be added
   */
  public void addWord( String s )
  {
    if( s.isEmpty() )
    {
      return;
    }

    if( wordTrie == null )
    {
      wordTrie = new Node( s.charAt( 0 ) );
    }

    Node node = wordTrie;
    char[] word = s.toCharArray();
    int index = 0;
    while( true )
    {
      if( word[index] < node.c )
      {
        if( node.left == null )
        {
          node.left = new Node( word[index] );
        }
        node = node.left;
      }
      else if( word[index] > node.c )
      {
        if( node.right == null )
        {
          node.right = new Node( word[index] );
        }
        node = node.right;
      }
      else
      {
        index++;
        if( index < word.length )
        {
          if( node.equal == null )
          {
            node.equal = new Node( word[index] );
          }
          node = node.equal;
        }
        else
        {
          node.isEnd = true;
          break;
        }
      }
    }
  }


  /**
   * Method to add an array of strings to the trie
   *
   * @param s Array of strings to be added
   */
  public void addAllWords( String[] s )
  {
    for( String word: s )
    {
      addWord( word );
    }
  }

  /**
   * Method to check of a string exists in the trie
   *
   * @param s String to be checked
   *
   * @return true if string exists
   */
  public boolean wordSearch( String s )
  {
    return findWord( s ) != null;
  }

  public Node findWord( String s )
  {
    if( isEmpty() )
    {
      return null;
    }

    int index = 0;
    Node node = wordTrie;
    char[] word = s.toCharArray();
    while( true )
    {
      if( node == null )
      {
        // not found
        return null;
      }

      if( word[index] < node.c )
      {
        node = node.left;
      }
      else if( word[index] > node.c )
      {
        node = node.right;
      }
      else if( index < word.length - 1 )
      {
        node = node.equal;
        index++;
      }
      else
      {
        return node;
      }
    }
  }


  /**
   * Method to check if the trie if empty
   * (No stirngs added yet)
   */
  public boolean isEmpty()
  {
    return wordTrie == null;
  }

  /**
   * Method to empty the trie
   */
  public void clear()
  {
    wordTrie = null;
  }


  /**
   * Getter for wordTire
   *
   * @return Node wordTrie
   */
  public Node getWordTrie()
  {
    return wordTrie;
  }

  /**
   * Method to search autocomplete options
   *
   * @param s Prefix string being searched for
   *
   * @return List of strings representing autocomplete options
   */
  public List<String> autoCompleteOptions( String s )
  {
    Node word = findWord( s );
    if( word == null )
    {
      return Collections.emptyList();
    }
    return complete( word.equal, s, new ArrayList<>() );
  }

  List<String> complete( Node node, String prefix, List<String> options )
  {
    if( node == null )
    {
      return options;
    }

    if( node.isEnd )
    {
      options.add( prefix + node.c );
    }

    complete( node.left, prefix, options );
    complete( node.equal, prefix + node.c, options );
    complete( node.right, prefix, options );
    return options;
  }
}
