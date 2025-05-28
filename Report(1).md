### Method 1: Plain String Format
1. **What are the pros and cons of using a plain string like `"LOGIN|user|pass"`?**
   
This method is simple and readable, but it is not suitable for complex data. Using plain strings for client-server data transfer can also lead to errors and processing issues.

3. **How would you parse it, and what happens if the delimiter appears in the data?**
   
You can extract the data using functions like ‍`split` and `replace`, but if the delimiter character exists within the data, it may cause incorrect transmission or storage, potentially leading to serious errors.

5. **Is this approach suitable for more complex or nested data?**
   
No, this approach is not ideal for complex data, as extracting information becomes difficult and inefficient.

### Method 2: Serialized Java Object

1. **What’s the advantage of sending a full Java object?**
   
This ensures that data is transmitted accurately without modification

3. **Could this work with a non-Java client like Python?**
   
It might be possible, but it would be challenging, as handling this format between Python and Java is not straightforward.

### Method 3: JSON

1. **Why is JSON often preferred for communication between different systems?**
It is widely used due to its simple structure and readability.
2. **Would this format work with servers or clients written in other languages?**
Yes, JSON is language-agnostic.
